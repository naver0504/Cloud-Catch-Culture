package com.example.userservice.kafka.message;

import com.example.userservice.entity.point_history.PointChange;
import com.example.userservice.entity.point_history.PointHistory;
import com.example.userservice.entity.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class VisitAuthMessage extends BaseMessage{
    private long userId;
    private int culturalEventId;

    private int sign;

    public void setSign(final int sign) {
        this.sign = sign;
    }

    public PointHistory toEntity() {

        final PointChange pointChange = PointChange.VISIT_AUTH;
        return PointHistory.builder()
                .messageId(getMessageId())
                .user(User.createUser(userId))
                .description(pointChange.getDescription())
                .pointChange(pointChange.getPoint())
                .build();
    }

    public String toString(final ObjectMapper objectMapper) {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
