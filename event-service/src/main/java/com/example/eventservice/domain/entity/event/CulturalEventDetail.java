package com.example.eventservice.domain.entity.event;

import com.example.eventservice.common.converter.StoredImageUrlConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.time.LocalDateTime;
import java.util.List;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class CulturalEventDetail {

    @Column(nullable = false, columnDefinition = "TEXT")
    @Convert(converter = StoredImageUrlConverter.class)
    private List<String> storedImageUrl;
    @Column(nullable = false)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime startDate;

    @Column(nullable = false)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime endDate;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String place;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String reservationLink;

    @JsonIgnore
    @Column(columnDefinition = "GEOMETRY")
    private Point geography;

    private String wayToCome;
    private String sns;
    private String telephone;
    private Boolean isFree;

    @PrePersist
    public void prePersist() {
        startDate = LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), startDate.getHour(), startDate.getMinute(), 0);
        endDate = LocalDateTime.of(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(), endDate.getHour(), endDate.getMinute(), 0);
    }

    public static Point createGeography(final Double latitude, final Double longitude) {
        if(longitude.equals(-200D) && latitude.equals(-200D)) {
            return null;
        }

        final String pointWKT = String.format("POINT(%s %s)", latitude, longitude);
        final Point point;
        try {
            point = (Point) new WKTReader().read(pointWKT);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return point;
    }

    public Double getLatitude() { return this.geography == null ? null : this.geography.getX();}

    public Double getLongitude() {
        return this.geography == null ? null : this.geography.getY();
    }


}
