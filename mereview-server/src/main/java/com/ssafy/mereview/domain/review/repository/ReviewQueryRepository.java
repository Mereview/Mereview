package com.ssafy.mereview.domain.review.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.mereview.api.service.review.dto.response.ReviewResponse;
import com.ssafy.mereview.domain.review.entity.Review;
import com.ssafy.mereview.domain.review.entity.ReviewEvaluationType;
import com.ssafy.mereview.domain.review.repository.dto.SearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.ssafy.mereview.domain.member.entity.QMember.member;
import static com.ssafy.mereview.domain.movie.entity.QMovie.movie;
import static com.ssafy.mereview.domain.review.entity.QReview.review;
import static com.ssafy.mereview.domain.review.entity.QReviewEvaluation.reviewEvaluation;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
@Repository
public class ReviewQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Review> searchByCondition(SearchCondition condition, Pageable pageable) {
        List<Long> ids = queryFactory
                .select(review.id)
                .from(review)
                .where(
                        isTitle(condition.getTitle()),
                        isContent(condition.getContent()),
                        isTerm(condition.getTerm())
                )
                .orderBy(sortByField(condition.getOrderBy()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }

        return queryFactory
                .select(review)
                .from(review)
                .join(review.member, member).fetchJoin()
                .join(review.movie, movie).fetchJoin()
                .where(review.id.in(ids))
                .orderBy(sortByField(condition.getOrderBy()))
                .fetch();

    }

//    // 사용 여부 미정 메소드
//    private int getReviewEvaluationCount(NumberPath<Long> reviewId, ReviewEvaluationType type) {
//        return queryFactory
//                .select(reviewEvaluation.count())
//                .from(reviewEvaluation)
//                .where(
//                        reviewEvaluation.review.id.eq(reviewId),
//                        reviewEvaluation.type.eq(type)
//                ).fetchFirst().intValue();
//    }

    public int getTotalPages(SearchCondition condition) {
        return queryFactory
                .select(review.count())
                .from(review)
                .join(review.member, member).fetchJoin()
                .join(review.movie, movie).fetchJoin()
                .where(
                        isTitle(condition.getTitle()),
                        isContent(condition.getContent()),
                        isTerm(condition.getTerm())
                )
                .fetchFirst().intValue();
    }

    public Review searchById(Long reviewId) {
        return queryFactory
                .select(review)
                .from(review)
                .join(review.member, member).fetchJoin()
                .join(review.movie, movie).fetchJoin()
                .where(
                        review.id.eq(reviewId)
                ).fetchOne();
    }

    private BooleanExpression isTitle(String title) {
        return hasText(title) ? review.title.like("%" + title + "%") : null;
    }

    private BooleanExpression isContent(String content) {
        return hasText(content) ? review.content.like("%" + content + "%") : null;
    }

    private BooleanExpression isTerm(String term) {
        if (hasText(term)) {
            LocalDateTime today = LocalDateTime.now();
            if (term.equals("weekly")) {
                return review.createdTime.between(today.minusDays(7), today);
            } else if (term.equals("semiannual")) {
                return review.createdTime.between(today.minusMonths(6), today);
            } else if (term.equals("yearly")) {
                return review.createdTime.between(today.minusYears(1), today);
            }
        }
        return null;
    }

    private OrderSpecifier<?> sortByField(String filedName) {
        Order order = Order.DESC;

        if (hasText(filedName)) {
            if (filedName.equals("hits")) {
                return new OrderSpecifier<>(order, review.hits);
            }
        }
        return new OrderSpecifier<>(order, review.createdTime);
    }
}
