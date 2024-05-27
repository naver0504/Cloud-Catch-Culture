package com.example.eventservice.service.review;

import com.example.eventservice.common.aop.kafka.KafkaTransactional;
import com.example.eventservice.common.aop.visitauth.AuthenticatedVisitAuth;
import com.example.eventservice.controller.dto.*;
import com.example.eventservice.domain.entity.review.Review;
import com.example.eventservice.domain.repository.review.ReviewQueryRepository;
import com.example.eventservice.domain.repository.review.ReviewRepository;
import com.example.eventservice.service.UserServiceClient;
import com.example.eventservice.service.s3.S3EventForDelete;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.example.eventservice.domain.entity.event.CulturalEvent.*;
import static com.example.eventservice.kafka.KafkaConstant.*;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewQueryRepository reviewQueryRepository;
    private final ReviewRepository reviewRepository;
    private final UserServiceClient userServiceClient;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ReviewRatingResponseDTO getReviewRating(final int culturalEventId) {
        return reviewQueryRepository.getReviewRating(culturalEventId);
    }


    @Transactional(readOnly = true)
    public ReviewResponseDTO getUserReview(final int culturalEventId, final long userId) {
        final Map<Long, String> map = userServiceClient.findUserNicknameByIds(List.of(userId)).stream()
                .collect(toMap(UserNicknameForFeign::userId, UserNicknameForFeign::nickname));

        if(map.isEmpty() || !map.containsKey(userId)) {
            throw new IllegalArgumentException("User not found");

        }
        return reviewRepository.findByCulturalEventIdAndUserId(culturalEventId, userId)
                .map(it -> ReviewResponseDTO.from(it, map.get(it.getUserId())))
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
    }

    @Transactional(readOnly = true)
    public Slice<ReviewResponseDTO> getReviewList(final int culturalEventId, final long userId, final int lastId) {
        final List<ReviewResponseDTO.ReviewResponseQueryDTO> queryResult = reviewQueryRepository.getReviewList(culturalEventId, userId, lastId);
        final List<Long> userIds = queryResult.stream()
                .map(ReviewResponseDTO.ReviewResponseQueryDTO::userId)
                .toList();
        
        final Map<Long, String> map = userServiceClient.findUserNicknameByIds(userIds).stream()
                .collect(toMap(UserNicknameForFeign::userId, UserNicknameForFeign::nickname));

        final List<ReviewResponseDTO> content = queryResult.stream()
                .filter(it -> map.containsKey(it.userId()))
                .map(it -> ReviewResponseDTO.from(it, map.get(it.userId())))
                .toList();

        if(queryResult.size() != content.size()) {
            throw new IllegalArgumentException("User not found");
        }

        return reviewQueryRepository.createSlice(content);
    }


    @AuthenticatedVisitAuth
    @KafkaTransactional(successTopic = REVIEW_POINT)
    public Review createReview(final int culturalEventId, final long userId, final List<String> storedImageUrl, final CreateReviewRequestDTO request) {

        final Review review = Review.builder()
                .userId(userId)
                .storedImageUrl(storedImageUrl)
                .level(request.level())
                .content(request.content())
                .culturalEvent(createCulturalEvent(culturalEventId))
                .build();

        return reviewRepository.save(review);
    }

    @AuthenticatedVisitAuth
    public void updateReview(final int culturalEventId, final long userId, final List<String> storedImageUrl, final UpdateReviewRequestDTO request) {
        final Review review = reviewRepository.findById(request.reviewId()).orElseThrow(() -> new IllegalArgumentException("Review not found"));

        if(review.getUserId() != userId) {
            throw new IllegalArgumentException("User not match");
        }
        if(review.getCulturalEvent().getId() != culturalEventId) {
            throw new IllegalArgumentException("Cultural event not match");
        }

        final List<String> storedImageUrlForDelete = review.getStoredImageUrl();
        applicationEventPublisher.publishEvent(new S3EventForDelete(storedImageUrlForDelete));

        review.update(storedImageUrl, request.content());
    }

    @AuthenticatedVisitAuth
    public void deleteReview(final int culturalEventId, final long userId, final int reviewId) {
        final Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("Review not found"));

        if(review.getUserId() != userId) {
            throw new IllegalArgumentException("User not match");
        }
        if(review.getCulturalEvent().getId() != culturalEventId) {
            throw new IllegalArgumentException("Cultural event not match");
        }

        review.delete();

        final List<String> storedImageUrlForDelete = review.getStoredImageUrl();
        applicationEventPublisher.publishEvent(new S3EventForDelete(storedImageUrlForDelete));
    }
}
