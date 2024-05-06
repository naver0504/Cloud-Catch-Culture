package com.example.reportservice.repository.event_report;

import com.example.reportservice.entity.event_report.EventReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface EventReportRepository extends JpaRepository<EventReport, Integer> {

    @Modifying
    @Query("update EventReport er set er.isAccepted = true where er.id = :id")
    void acceptEventReport(int id);

    @Modifying
    @Query("update EventReport er set er.isAccepted = false where er.userId = :userId and er.culturalEventDetail.title = :title and er.culturalEventDetail.startDate = :startDate and er.culturalEventDetail.endDate = :endDate")
    void unAcceptEventReport(long userId, String title, LocalDateTime startDate, LocalDateTime endDate);
}
