package com.example.reportservice.repository.event_report;

import com.example.reportservice.entity.event_report.EventReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EventReportRepository extends JpaRepository<EventReport, Integer> {

    @Modifying
    @Query("update EventReport er set er.isAccepted = true where er.id = :id")
    void acceptEventReport(int id);
}
