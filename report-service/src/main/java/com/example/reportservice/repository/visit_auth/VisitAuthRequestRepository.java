package com.example.reportservice.repository.visit_auth;

import com.example.reportservice.entity.VisitAuthRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VisitAuthRequestRepository extends JpaRepository<VisitAuthRequest, Integer> {

    @Query(value = "update VisitAuthRequest v set  v.isAuthenticated = true where v.id = :visitAuthRequestId")
    @Modifying
    void authenticateVisitAuthRequest(int visitAuthRequestId);

    @Query(value = "update VisitAuthRequest v set  v.isAuthenticated = false where v.userId = :userId and v.culturalEventId = :culturalEventId")
    @Modifying
    void unAuthenticateVisitAuthRequest(long userId, int culturalEventId);
}
