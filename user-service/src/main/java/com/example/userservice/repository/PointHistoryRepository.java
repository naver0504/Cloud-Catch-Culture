package com.example.userservice.repository;

import com.example.userservice.entity.point_history.PointChange;
import com.example.userservice.entity.point_history.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Integer> {


    @Query("select ph from PointHistory ph where ph.user.id = :userId and  ph.culturalEventId = :cultureEventId and ph.pointChange = :pointChange")
    Optional<PointHistory> findByUserIdAndCulturalEventIdAndPointChange(long userId, int cultureEventId, PointChange pointChange);

}
