package com.example.eventservice.entity.event;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class CulturalEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Embedded
    private CulturalEventDetail culturalEventDetail;

    private int viewCount;
    private int likeCount;
    private int starCount;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.viewCount = 0;
        this.likeCount = 0;
    }

    public static CulturalEvent createCulturalEvent(final int culturalEventId) {
        return CulturalEvent.builder()
                .id(culturalEventId)
                .build();
    }
}
