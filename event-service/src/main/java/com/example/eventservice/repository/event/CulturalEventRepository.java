package com.example.eventservice.repository.event;

import com.example.eventservice.entity.event.CulturalEvent;
import com.example.eventservice.entity.event.CulturalEventDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface CulturalEventRepository extends JpaRepository<CulturalEvent, Long>{


    @Query(value = "select * from cultural_event as c where c.id = :culturalEventId for update", nativeQuery = true)
    Optional<CulturalEvent> findByIdForUpdate(int culturalEventId);

    @Query("SELECT c.culturalEventDetail FROM CulturalEvent c WHERE c.id = :culturalEventId")
    CulturalEventDetail getCulturalEventDetails(int culturalEventId);

    @Query("update CulturalEvent c set c.viewCount = c.viewCount + 1 where c.id = :culturalEventId")
    @Modifying(clearAutomatically = true)
    void updateViewCount(int culturalEventId);

    @Query("update CulturalEvent c set c.likeCount = c.likeCount + :count where c.id = :culturalEventId")
    @Modifying(clearAutomatically = true)
    void updateLikeCount(int culturalEventId, int count);

    @Query("update CulturalEvent c set c.starCount = c.starCount + :count where c.id = :culturalEventId")
    @Modifying(clearAutomatically = true)
    void updateStarCount(int culturalEventId, int count);
}
