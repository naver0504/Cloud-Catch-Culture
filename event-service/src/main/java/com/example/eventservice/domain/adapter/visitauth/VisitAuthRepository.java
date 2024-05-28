package com.example.eventservice.domain.adapter.visitauth;

import com.example.eventservice.domain.entity.visitauth.VisitAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VisitAuthRepository extends JpaRepository<VisitAuth, Integer> {

    @Query("delete from VisitAuth va where va.userId = :userId and va.culturalEvent.id = :eventId")
    @Modifying
    void deleteByUserIdAndEventId(long userId, int eventId);

    Optional<VisitAuth> findByUserIdAndCulturalEventId(long userId, int culturalEventId);
}
