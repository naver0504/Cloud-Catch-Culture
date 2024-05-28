package com.example.reportservice.domain.adapter.event_report;

import com.example.reportservice.domain.entity.event_report.EventReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EventReportRepository extends JpaRepository<EventReport, Integer> {

    @Query("select er from EventReport er where er.userId = :userId and er.culturalEventDetail.title = :title and er.culturalEventDetail.startDate = :startDate and er.culturalEventDetail.endDate = :endDate")
    Optional<EventReport> findByUserIdAndCulturalEventDetail(long userId, String title, LocalDateTime startDate, LocalDateTime endDate);
}
