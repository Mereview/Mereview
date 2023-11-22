package com.ssafy.mereview.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.mereview.domain.member.entity.MemberVisit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


import static com.ssafy.mereview.domain.member.entity.QMemberVisit.memberVisit;

@Repository
@Getter
@RequiredArgsConstructor
public class MemberVisitQueryRepository {
    private final JPAQueryFactory queryFactory;

    public MemberVisit searchVisitByMemberId(Long memberId) {
        return queryFactory.selectFrom(memberVisit)
                .where(memberVisit.member.id.eq(memberId))
                .fetchOne();
    }
}
