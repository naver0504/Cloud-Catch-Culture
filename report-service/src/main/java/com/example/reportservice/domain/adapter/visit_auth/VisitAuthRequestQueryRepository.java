package com.example.reportservice.domain.adapter.visit_auth;

import com.example.reportservice.common.constant.VisitAuthConstant;
import com.example.reportservice.domain.adapter.visit_auth.query.WhereQuery;
import com.example.reportservice.dto.visit_auth.VisitAuthRequestResponseDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.reportservice.domain.entity.visit_auth.QVisitAuthRequest.visitAuthRequest;

@Repository
@RequiredArgsConstructor
public class VisitAuthRequestQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final int PAGE_SIZE = 10;

    public Slice<VisitAuthRequestResponseDTO> getVisitAuthRequestList(final int lastId, final VisitAuthConstant visitAuthConstant) {
        final List<VisitAuthRequestResponseDTO> content = queryFactory.select(Projections.constructor(
                        VisitAuthRequestResponseDTO.class,
                        visitAuthRequest.id,
                        visitAuthRequest.createdAt,
                        visitAuthRequest.isAuthenticated
                ))
                .from(visitAuthRequest)
                .where(
                        WhereQuery.visitAuthRequestIdGt(lastId),
                        WhereQuery.visitAuthRequestConstantEq(visitAuthConstant)
                )
                .orderBy(visitAuthRequest.id.asc())
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
