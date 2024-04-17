package com.example.reportservice.entity.event_report;

import com.example.reportservice.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

}
