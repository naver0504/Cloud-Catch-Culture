package com.example.reportservice.domain.adapter.visit_auth;

import com.example.reportservice.domain.entity.visit_auth.VisitAuthRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VisitAuthRequestRepository extends JpaRepository<VisitAuthRequest, Integer> {

    @Query("select vr from VisitAuthRequest vr where vr.userId = :userId and vr.culturalEventId = :culturalEventId")
    Optional<VisitAuthRequest> findByUserIdAndCulturalEventId(long userId, int culturalEventId);
}
