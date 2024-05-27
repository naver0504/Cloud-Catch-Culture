package com.example.userservice.kafka.message;

import com.example.userservice.entity.point_history.PointChange;
import com.example.userservice.entity.point_history.PointHistory;
import com.example.userservice.entity.user.User;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VisitAuthMessage extends BaseMessage{


    @Override
    public PointHistory toEntity() {

        return PointHistory.builder()
                .culturalEventId(getCulturalEventId())
                .user(User.createUser(this.getUserId()))
                .pointChange(getPointChange())
                .build();
    }

    @Override
    public PointChange getPointChange() {
        return PointChange.VISIT_AUTH;
    }


}
