package com.example.reportservice.repository.event_report;

import com.example.reportservice.common.constant.EventReportConstant;
import com.example.reportservice.dto.event_report.EventReportResponseDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.reportservice.entity.event_report.QEventReport.eventReport;
import static com.example.reportservice.repository.event_report.where.WhereQuery.eventReportConstantEq;
import static com.example.reportservice.repository.event_report.where.WhereQuery.eventReportIdGt;

@Repository
@RequiredArgsConstructor
public class EventReportQueryRepository {

    private final JPAQueryFactory queryFactory;

    private final int PAGE_SIZE = 10;

    public Slice<EventReportResponseDTO> getEventReportList(final int lastId, final EventReportConstant eventReportConstant) {
        final List<EventReportResponseDTO> content = queryFactory.select(
                        Projections.constructor(
                                EventReportResponseDTO.class,
                                eventReport.id,
                                eventReport.createdAt,
                                eventReport.culturalEventDetail.title,
                                eventReport.isAccepted
                        )
                )
                .from(eventReport)
                .where(
                        eventReportIdGt(lastId),
                        eventReportConstantEq(eventReportConstant)
                )
                .orderBy(eventReport.id.asc())
                .limit(PAGE_SIZE + 1)
                .fetch();

        boolean hasNext = false;

        if(content.size() == PAGE_SIZE + 1l) {
            content.remove(PAGE_SIZE);
            hasNext = true;
        }

        return new SliceImpl<>(content, PageRequest.ofSize(PAGE_SIZE), hasNext);
    }
}
