package com.ssafy.mereview.domain.review.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.mereview.api.service.review.dto.response.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.ssafy.mereview.domain.member.entity.QMember.member;
import static com.ssafy.mereview.domain.review.entity.QNotification.notification;
import static com.ssafy.mereview.domain.review.entity.QReview.review;

@RequiredArgsConstructor
@Repository
public class NotificationQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<NotificationResponse> searchByMemberId(Long memberId) {
        List<Long> ids = queryFactory
                .select(notification.id)
                .from(notification)
                .join(notification.member, member)
                .join(notification.review, review)
                .where(notification.id.eq(memberId))
                .fetch();

        if (ids.isEmpty()) {
            return new ArrayList<>();
        }

        return queryFactory
                .select(Projections.constructor(NotificationResponse.class,
                        notification.id,
                        notification.member.id,
                        notification.review.id,
                        notification.status,
                        notification.createdTime
                ))
                .from(notification)
                .join(notification.member, member)
                .join(notification.review, review)
                .where(notification.id.in(ids))
                .fetch();
    }

    public int countByMemberId(Long memberId) {
        Long result = queryFactory
                .select(notification.id.count())
                .from(notification)
                .join(notification.member, member)
                .join(notification.review, review)
                .where(notification.member.id.eq(memberId))
                .fetchOne();
        return result == null ? 0 : result.intValue();
    }
}
