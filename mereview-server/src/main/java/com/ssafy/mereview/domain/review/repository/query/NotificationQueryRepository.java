package com.ssafy.mereview.domain.review.repository.query;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.mereview.api.service.review.dto.response.NotificationResponse;
import com.ssafy.mereview.domain.review.entity.Notification;
import com.ssafy.mereview.domain.review.entity.NotificationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ssafy.mereview.domain.member.entity.QMember.member;
import static com.ssafy.mereview.domain.review.entity.QNotification.notification;
import static com.ssafy.mereview.domain.review.entity.QReview.review;
import static org.springframework.util.StringUtils.hasText;

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
                .where(isMember(memberId))
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
                .where(isMember(memberId))
                .fetchOne();
        return result == null ? 0 : result.intValue();
    }

    public List<Long> searchReviewIdsByMemberIdAndStatus(Long memberId, String status) {
        return queryFactory
                .select(notification.review.id)
                .from(notification)
                .where(
                        isMember(memberId),
                        isStatus(status)
                )
                .fetch();
    }

    public Optional<Notification> searchByReviewIdAndMemberId(Long reviewId, Long memberId) {
        return Optional.ofNullable(queryFactory
                .select(notification)
                .from(notification)
                .where(
                        isMember(memberId),
                        isReview(reviewId)
                )
                .fetchOne());
    }

    private BooleanExpression isMember(Long memberId) {
        return notification.member.id.eq(memberId);
    }

    private BooleanExpression isStatus(String status) {
        return hasText(status) ? notification.status.eq(NotificationStatus.valueOf(status)) : null;
    }

    private BooleanExpression isReview(Long reviewId) {
        return notification.review.id.eq(reviewId);
    }
}
