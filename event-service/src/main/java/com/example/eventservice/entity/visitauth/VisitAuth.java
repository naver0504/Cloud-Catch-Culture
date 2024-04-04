package com.example.eventservice.entity.visitauth;

import com.example.eventservice.common.converter.StoredImageUrlConverter;
import com.example.eventservice.entity.event.CulturalEvent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class VisitAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = StoredImageUrlConverter.class)
    private List<String> storedImageUrl;

    private boolean isAuthenticated;

    private LocalDateTime createdAt;

    @Column(nullable = false)
    private long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cultural_event_id")
    private CulturalEvent culturalEvent;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.isAuthenticated = false;
    }
}