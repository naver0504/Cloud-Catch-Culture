package com.example.eventservice.domain.entity.review;

import com.example.eventservice.common.converter.StoredImageUrlConverter;
import com.example.eventservice.domain.entity.event.CulturalEvent;
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
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Convert(converter = StoredImageUrlConverter.class)
    private List<String> storedImageUrl;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private long userId;

    private LocalDateTime createdAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cultural_event_id")
    private CulturalEvent culturalEvent;

    private boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Level level;

    @PrePersist
    public void prePersist() {
        this.isDeleted = false;
        this.createdAt = LocalDateTime.now();
    }

    public void delete() {
        this.isDeleted = true;
    }

    public void update(List<String> storedImageUrl, String content) {
        this.storedImageUrl = storedImageUrl;
        this.content = content;
    }
}
