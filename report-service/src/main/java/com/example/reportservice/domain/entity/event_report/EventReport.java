package com.example.reportservice.domain.entity.event_report;

import com.example.reportservice.domain.entity.BaseEntity;
import com.example.reportservice.kafka.message.BaseMessage;
import com.example.reportservice.kafka.message.EventReportMessage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EventReport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Embedded
    private CulturalEventDetail culturalEventDetail;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false)
    private boolean isAccepted;


    public static EventReport createEventReport(final long userId, final CulturalEventDetail culturalEventDetail) {
        return EventReport.builder()
                .userId(userId)
                .culturalEventDetail(culturalEventDetail)
                .build();
    }

    @Override
    public BaseMessage toBaseMessage() {
        return new EventReportMessage(this.userId, this.culturalEventDetail);
    }

    public void accept() {
        this.isAccepted = true;
    }

    public void unAccept() {
        this.isAccepted = false;
    }
}
