package com.example.eventservice.repository.review;

import com.example.eventservice.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("select r from Review r where r.culturalEvent.id = :culturalEventId and r.userId = :userId")
    Optional<Review> findByCulturalEventIdAndUserId(int culturalEventId, long userId);

    @Modifying
    @Query("delete from Review r where r.culturalEvent.id = :culturalEventId and r.userId = :userId")
    void deleteByCulturalEventIdAndUserId(int culturalEventId, long userId);
}
