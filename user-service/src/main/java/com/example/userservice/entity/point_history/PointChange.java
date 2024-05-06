package com.example.userservice.entity.point_history;

import com.example.userservice.kafka.message.BaseMessage;
import com.example.userservice.kafka.message.EventReportMessage;
import com.example.userservice.kafka.message.VisitAuthMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PointChange {

    REVIEW("리뷰 작성", 50),
    VISIT_AUTH("방문 인증", 50),
    CREATE_CULTURAL_EVENT("문화행사 등록", 100);

    private final String description;
    private final int point;

    public static PointChange getPointChangeFromMessage(final BaseMessage message) {
        if(message instanceof VisitAuthMessage) return VISIT_AUTH;
        if (message instanceof EventReportMessage) return CREATE_CULTURAL_EVENT;
        throw new IllegalArgumentException("Invalid message type");
    }
}
