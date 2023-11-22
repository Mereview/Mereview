package com.ssafy.mereview.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.mereview.domain.member.entity.MemberFollow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

import static com.ssafy.mereview.domain.member.entity.QMemberFollow.memberFollow;

@Repository
@RequiredArgsConstructor
public class MemberFollowQueryRepository {

    private final JPAQueryFactory queryFactory;
    public MemberFollow searchByTargetAndCurrentMember(Long targetId, Long currentMemberId) {
        return queryFactory.selectFrom(memberFollow)
                .where(memberFollow.targetMember.id.eq(targetId)
                        .and(memberFollow.member.id.eq(currentMemberId)))
                .fetchOne();
    }

    public List<MemberFollow> searchFollowing(Long id){
        return queryFactory.selectFrom(memberFollow)
                .where(memberFollow.member.id.eq(id))
                .fetch();
    }

    public List<MemberFollow> searchFollower(Long id){
        return queryFactory.selectFrom(memberFollow)
                .where(memberFollow.targetMember.id.eq(id))
                .fetch();
    }
}
