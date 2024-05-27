package com.example.eventservice.kafka.message;

import com.example.eventservice.domain.entity.review.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class ReviewMessage extends BaseMessage {

    public ReviewMessage(long userId, int id) {
        super(userId, id);
    }

    public static ReviewMessage of(Review review) {
        return new ReviewMessage(review.getUserId(), review.getCulturalEvent().getId());
    }
}
