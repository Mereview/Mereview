package com.ssafy.mereview.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.mereview.domain.member.entity.MemberTier;
import com.ssafy.mereview.domain.member.entity.QMemberTier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.ssafy.mereview.domain.member.entity.QMemberTier.memberTier;

@Repository
@RequiredArgsConstructor
public class MemberTierQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MemberTier searchMemberTierByMemberId(Long memberId, Long genreId){
        return queryFactory.selectFrom(memberTier)
                .where(memberTier.member.id.eq(memberId)
                        .and(memberTier.genre.id.eq(genreId)))
                .fetchOne();
    }

}
