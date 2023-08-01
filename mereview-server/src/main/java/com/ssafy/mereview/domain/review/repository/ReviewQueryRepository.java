package com.ssafy.mereview.domain.review.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.mereview.api.service.review.dto.response.ReviewDetailResponse;
import com.ssafy.mereview.domain.review.entity.Review;
import com.ssafy.mereview.domain.review.repository.dto.SearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.ssafy.mereview.domain.member.entity.QMember.member;
import static com.ssafy.mereview.domain.movie.entity.QMovie.movie;
import static com.ssafy.mereview.domain.review.entity.QReview.review;
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
                        isContent(condition.getContent())
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

    public int getTotalPages(SearchCondition condition) {
        return queryFactory
                .select(review.count())
                .from(review)
                .join(review.member, member).fetchJoin()
                .join(review.movie, movie).fetchJoin()
                .where(
                        isTitle(condition.getTitle()),
                        isContent(condition.getContent())
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

    private OrderSpecifier<?> sortByField(String filedName) {
        Order order = Order.DESC;

        if (filedName.equals("hits")) {
            return new OrderSpecifier<>(order, review.hits);
        }
        return new OrderSpecifier<>(order, review.createdTime);
    }

    private BooleanExpression isTitle(String title) {
        return hasText(title) ? review.title.like("%" + title + "%") : null;
    }

    private BooleanExpression isContent(String content) {
        return hasText(content) ? review.content.like("%" + content + "%") : null;
    }
}
