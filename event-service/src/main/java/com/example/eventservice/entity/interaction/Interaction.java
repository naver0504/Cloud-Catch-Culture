package com.example.eventservice.entity.interaction;

import com.example.eventservice.entity.event.CulturalEvent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.example.eventservice.entity.event.CulturalEvent.*;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Interaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cultural_event_id")
    private CulturalEvent culturalEvent;

    @Enumerated(EnumType.STRING)
    private LikeStar likeStar;

    private LocalDateTime createdAt;

    @PrePersist
    public void createdAt() {
        this.createdAt = LocalDateTime.now();
    }

    public static Interaction createInteraction(final int culturalEventId, final long userId, final LikeStar likeStar) {
        return Interaction.builder()
                .userId(userId)
                .culturalEvent(createCulturalEvent(culturalEventId))
                .likeStar(likeStar)
                .build();
    }

}
