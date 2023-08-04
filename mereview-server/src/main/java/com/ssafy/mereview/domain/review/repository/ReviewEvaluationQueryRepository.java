package com.ssafy.mereview.domain.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.mereview.domain.review.entity.ReviewEvaluation;
import com.ssafy.mereview.domain.review.entity.ReviewEvaluationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ssafy.mereview.domain.review.entity.QReviewEvaluation.reviewEvaluation;

@RequiredArgsConstructor
@Repository
public class ReviewEvaluationQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<ReviewEvaluation> searchByReviewAndMember(Long reviewId, Long memberId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(reviewEvaluation)
                .where(
                        reviewEvaluation.review.id.eq(reviewId),
                        reviewEvaluation.member.id.eq(memberId)
                )
                .fetchOne());
    }

    public Map<ReviewEvaluationType, Integer> getCountByReviewIdGroupedByType(Long reviewId) {
        return queryFactory
                .select(reviewEvaluation.type, reviewEvaluation.count())
                .from(reviewEvaluation)
                .where(reviewEvaluation.review.id.eq(reviewId))
                .groupBy(reviewEvaluation.type)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(reviewEvaluation.type),
                        tuple -> Objects.requireNonNull(tuple.get(reviewEvaluation.count())).intValue()
                ));
    }
}
