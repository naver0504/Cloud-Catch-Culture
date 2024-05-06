package com.example.userservice.kafka.message;

import com.example.userservice.entity.point_history.PointChange;
import com.example.userservice.entity.point_history.PointHistory;
import com.example.userservice.entity.user.User;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class VisitAuthMessage extends BaseMessage{

    private final PointChange pointChange = PointChange.VISIT_AUTH;
    @Override
    public PointHistory toEntity() {

        return PointHistory.builder()
                .culturalEventId(getCulturalEventId())
                .user(User.createUser(this.getUserId()))
                .pointChange(pointChange)
                .build();
    }


}
