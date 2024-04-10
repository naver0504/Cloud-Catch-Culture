package com.example.reportservice.entity;

import com.example.reportservice.converter.StoredFileUrlConverter;
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
public class VisitAuthRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = StoredFileUrlConverter.class)
    private List<String> storedFileUrl;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false)
    private int culturalEventId;

    private LocalDateTime createdAt;

    private boolean isAuthenticated;


    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}
