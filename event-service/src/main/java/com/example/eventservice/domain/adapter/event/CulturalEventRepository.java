package com.example.eventservice.domain.adapter.event;

import com.example.eventservice.domain.entity.event.CulturalEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;


public interface CulturalEventRepository extends JpaRepository<CulturalEvent, Integer>{



    @Query("update CulturalEvent c set c.viewCount = c.viewCount + 1 where c.id = :culturalEventId")
    @Modifying(clearAutomatically = true)
    void updateViewCount(int culturalEventId);

    @Query("update CulturalEvent c set c.likeCount = c.likeCount + :count where c.id = :culturalEventId")
    @Modifying(clearAutomatically = true)
    void updateLikeCount(int culturalEventId, int count);

    @Query("update CulturalEvent c set c.starCount = c.starCount + :count where c.id = :culturalEventId")
    @Modifying(clearAutomatically = true)
    void updateStarCount(int culturalEventId, int count);

    @Query("select c from CulturalEvent c where c.culturalEventDetail.title = :title and c.culturalEventDetail.place = :place " +
            "and c.culturalEventDetail.startDate = :startDate and c.culturalEventDetail.endDate = :endDate")
    Optional<CulturalEvent> findByCulturalEventDetail(String title, String place, LocalDateTime startDate, LocalDateTime endDate);

    @Deprecated
    @Query(value = "select * from cultural_event as c where c.id = :culturalEventId for update", nativeQuery = true)
    Optional<CulturalEvent> findByIdForUpdate(int culturalEventId);

}
