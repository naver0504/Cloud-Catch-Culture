package com.example.eventservice.domain.repository.review;

import com.example.eventservice.controller.dto.ReviewRatingResponseDTO;
import com.example.eventservice.domain.entity.event.QCulturalEvent;
import com.example.eventservice.domain.entity.review.QReview;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.eventservice.controller.dto.ReviewResponseDTO.*;
import static com.example.eventservice.domain.repository.event.query.WhereQuery.culturalEventIdEq;
import static com.example.eventservice.domain.repository.review.query.WhereQuery.*;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final int REVIEW_PAGE_SIZE = 13;



    public ReviewRatingResponseDTO getReviewRating(final int culturalEventId) {

        final List<ReviewRatingResponseDTO.ReviewRatingQueryDTO> result = queryFactory.select(Projections.constructor(
                        ReviewRatingResponseDTO.ReviewRatingQueryDTO.class,
                        QReview.review.level,
                        QReview.review.level.count().as("count")
                ))
                .from(QReview.review)
                .innerJoin(QReview.review.culturalEvent, QCulturalEvent.culturalEvent)
                .where(culturalEventIdEq(culturalEventId))
                .groupBy(QReview.review.level)
                .fetch();

        long totalCount = 0;
        double sum = 0;

        for(ReviewRatingResponseDTO.ReviewRatingQueryDTO reviewRatingQueryDTO : result) {
            totalCount += reviewRatingQueryDTO.count();
            sum += reviewRatingQueryDTO.level().getPoint() * reviewRatingQueryDTO.count();
        }

        return new ReviewRatingResponseDTO(sum / totalCount, result);

    }

    public List<ReviewResponseQueryDTO> getReviewList(final int culturalEventId, final long userId, final int lastId) {
        return queryFactory.select(Projections.fields(ReviewResponseQueryDTO.class,
                        QReview.review.id.as("reviewId"),
                        QReview.review.userId,
                        QReview.review.content,
                        QReview.review.storedImageUrl,
                        QReview.review.level,
                        QReview.review.createdAt
                ))
                .from(QReview.review)
                .innerJoin(QReview.review.culturalEvent, QCulturalEvent.culturalEvent)
                .where(
                        culturalEventIdEq(culturalEventId),
                        reviewIdGt(lastId),
                        reviewIsDelEq(false),
                        userIdNotEq(userId)

                )
                .orderBy(QReview.review.createdAt.desc())
                .limit(REVIEW_PAGE_SIZE)
                .fetch();
    }


    public <T> Slice<T> createSlice(final List<T> content) {

        boolean hasNext = false;

        if(content.size() == REVIEW_PAGE_SIZE + 1L) {
            content.remove(REVIEW_PAGE_SIZE);
            hasNext = true;
        }
        return new SliceImpl<>(content, PageRequest.ofSize(REVIEW_PAGE_SIZE), hasNext);
    }
}
