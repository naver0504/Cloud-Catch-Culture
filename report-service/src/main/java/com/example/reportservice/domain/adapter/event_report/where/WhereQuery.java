package com.example.reportservice.domain.adapter.event_report.where;

import com.example.reportservice.common.constant.EventReportConstant;
import com.querydsl.core.types.dsl.BooleanExpression;

import static com.example.reportservice.domain.entity.event_report.QEventReport.eventReport;

public class WhereQuery {

    public static BooleanExpression eventReportIdGt(final int eventReportId) {
        return eventReport.id.gt(eventReportId);
    }

    public static BooleanExpression eventReportConstantEq(final EventReportConstant eventReportConstant) {
        return switch (eventReportConstant) {
            case ALL -> null;
            case ACCEPTED -> eventReport.isAccepted.eq(true);
        };
    }
}
