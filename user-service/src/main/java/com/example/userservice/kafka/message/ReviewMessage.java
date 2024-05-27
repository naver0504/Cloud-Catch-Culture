package com.example.userservice.kafka.message;

import com.example.userservice.entity.point_history.PointChange;
import com.example.userservice.entity.point_history.PointHistory;
import com.example.userservice.entity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class ReviewMessage extends BaseMessage {

    @Override
    public PointHistory toEntity() {
        return PointHistory.builder()
                .culturalEventId(this.getCulturalEventId())
                .pointChange(this.getPointChange())
                .user(User.createUser(this.getUserId()))
                .build();
    }

    @Override
    public PointChange getPointChange() {
        return PointChange.REVIEW;
    }
}
