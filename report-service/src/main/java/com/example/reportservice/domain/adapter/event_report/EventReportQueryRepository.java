package com.example.reportservice.domain.adapter.event_report;

import com.example.reportservice.common.constant.EventReportConstant;
import com.example.reportservice.domain.adapter.event_report.where.WhereQuery;
import com.example.reportservice.dto.event_report.EventReportResponseDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.reportservice.domain.entity.event_report.QEventReport.eventReport;

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
                        WhereQuery.eventReportIdGt(lastId),
                        WhereQuery.eventReportConstantEq(eventReportConstant)
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
