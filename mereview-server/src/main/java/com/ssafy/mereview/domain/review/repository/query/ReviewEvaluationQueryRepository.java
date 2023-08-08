package com.ssafy.mereview.domain.review.repository.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.mereview.domain.review.entity.ReviewEvaluation;
import com.ssafy.mereview.domain.review.entity.ReviewEvaluationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ssafy.mereview.domain.review.entity.QReviewEvaluation.reviewEvaluation;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ReviewEvaluationQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<ReviewEvaluation> searchByReviewAndMember(Long reviewId, Long memberId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(reviewEvaluation)
                .where(
                        isReview(reviewId),
                        reviewEvaluation.member.id.eq(memberId)
                )
                .fetchOne());
    }

    public Map<ReviewEvaluationType, Integer> getCountByReviewIdGroupedByType(Long reviewId) {
        return queryFactory
                .select(reviewEvaluation.type, reviewEvaluation.count())
                .from(reviewEvaluation)
                .where(isReview(reviewId))
                .groupBy(reviewEvaluation.type)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(reviewEvaluation.type),
                        tuple -> Objects.requireNonNull(tuple.get(reviewEvaluation.count())).intValue()
                ));
    }

    public int getCountByReviewIdAndType(Long reviewId, ReviewEvaluationType type) {
        Long result = queryFactory
                .select(reviewEvaluation.count())
                .from(reviewEvaluation)
                .where(
                        isReview(reviewId),
                        isType(type)
                )
                .groupBy(reviewEvaluation.type)
                .fetchFirst();

        return result == null ? 0 : result.intValue();
    }

    private BooleanExpression isReview(Long reviewId) {
        return reviewEvaluation.review.id.eq(reviewId);
    }

    private BooleanExpression isType(ReviewEvaluationType type) {
        return reviewEvaluation.type.eq(type);
    }
}
