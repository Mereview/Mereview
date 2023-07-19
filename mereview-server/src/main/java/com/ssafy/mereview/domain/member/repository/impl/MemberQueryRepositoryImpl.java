package com.ssafy.mereview.domain.member.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.repository.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import static com.ssafy.mereview.domain.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Member searchByEmail(String email) {
        return queryFactory
                .select(member)
                .from(member)
                .where(member.email.eq(email))
                .fetchOne();
    }

}
