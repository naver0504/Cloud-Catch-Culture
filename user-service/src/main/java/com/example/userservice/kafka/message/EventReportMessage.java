package com.example.userservice.kafka.message;

import com.example.userservice.entity.point_history.PointChange;
import com.example.userservice.entity.point_history.PointHistory;
import com.example.userservice.entity.user.User;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class EventReportMessage extends BaseMessage{


    public record CulturalEventDetail(String title, String place, LocalDateTime startDate, LocalDateTime endDate) {}

    private final PointChange pointChange = PointChange.CREATE_CULTURAL_EVENT;
    private CulturalEventDetail culturalEventDetail;

    @Override
    public PointHistory toEntity() {

        return PointHistory.builder()
                .culturalEventId(getCulturalEventId())
                .user(User.createUser(this.getUserId()))
                .pointChange(pointChange)
                .build();
    }
}
