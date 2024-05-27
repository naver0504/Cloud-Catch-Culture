package com.example.eventservice.controller;

import com.example.eventservice.controller.dto.CreateReviewRequestDTO;
import com.example.eventservice.controller.dto.ReviewRatingResponseDTO;
import com.example.eventservice.controller.dto.ReviewResponseDTO;
import com.example.eventservice.controller.dto.UpdateReviewRequestDTO;
import com.example.eventservice.service.s3.S3Service;
import com.example.eventservice.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final S3Service s3Service;

    @GetMapping("/{culturalEventId}/rating")
    public ResponseEntity<ReviewRatingResponseDTO> getReviewRating(final @PathVariable int culturalEventId) {
        return ResponseEntity.ok(reviewService.getReviewRating(culturalEventId));
    }

    @GetMapping("/{culturalEventId}/my-review")
    public ResponseEntity<ReviewResponseDTO> getUserReview(final @PathVariable int culturalEventId, final @RequestHeader("userId") long userId) {
        return ResponseEntity.ok(reviewService.getUserReview(culturalEventId, userId));
    }

    @GetMapping("/{culturalEventId}")
    public ResponseEntity<Slice<ReviewResponseDTO>> getReviewList(final @PathVariable  int culturalEventId,
                                                                                         final @RequestHeader("userId") long userId,
                                                                                         final @RequestParam(required = false, defaultValue = "0") int lastId) {
        return ResponseEntity.ok(reviewService.getReviewList(culturalEventId, userId, lastId));
    }

    @PostMapping(value = "/{culturalEventId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createReview(final @PathVariable int culturalEventId,
                                             final @RequestHeader("userId") long userId,
                                             final @RequestPart List<MultipartFile> fileList,
                                             final @RequestPart CreateReviewRequestDTO request) {

        List<String> storedImageUrl = fileList.stream().map(s3Service::uploadFile).toList();
        reviewService.createReview(culturalEventId, userId, storedImageUrl, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PatchMapping(value = "/{culturalEventId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateReview(final @PathVariable int culturalEventId,
                                             final @RequestHeader("userId") long userId,
                                             final @RequestPart List<MultipartFile> fileList,
                                             final @RequestPart UpdateReviewRequestDTO request) {

        final List<String> storedImageUrl = fileList.stream().map(s3Service::uploadFile).toList();
        reviewService.updateReview(culturalEventId, userId, storedImageUrl, request);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @DeleteMapping("/{culturalEventId}/{reviewId}")
    public ResponseEntity<Void> deleteReview(final @PathVariable int culturalEventId,
                                             final @RequestHeader("userId") long userId,
                                             final @PathVariable int reviewId) {
        reviewService.deleteReview(culturalEventId, userId, reviewId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }



}
