package com.ssafy.mereview.domain.member.repository;

import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.mereview.domain.member.entity.QInterest.interest;
import static com.ssafy.mereview.domain.member.entity.QMember.member;

@RequiredArgsConstructor
@Repository
public class MemberInterestQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Long> searchRandomMember(Long genreId, int count) {
        return queryFactory
                .select(interest.member.id)
                .from(interest)
                .join(interest.member, member)
                .where(interest.genre.id.eq(genreId))
                .orderBy(NumberExpression.random().desc())
                .limit(count)
                .fetch();
    }
}
