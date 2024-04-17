package com.example.reportservice.entity.visit_auth;

import com.example.reportservice.common.converter.StoredFileUrlConverter;
import com.example.reportservice.entity.BaseEntity;
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
public class VisitAuthRequest extends BaseEntity {

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


    private boolean isAuthenticated;

}
