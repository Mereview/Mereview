package com.ssafy.mereview.domain.review.repository.query;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.mereview.domain.member.entity.QMember;
import com.ssafy.mereview.domain.review.entity.Review;
import com.ssafy.mereview.domain.review.repository.dto.SearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;
import static com.ssafy.mereview.domain.member.entity.QInterest.interest;
import static com.ssafy.mereview.domain.member.entity.QMember.member;
import static com.ssafy.mereview.domain.movie.entity.QMovie.movie;
import static com.ssafy.mereview.domain.review.entity.QReview.review;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
@Repository
public class ReviewQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Review> searchByCondition(SearchCondition condition, Pageable pageable) {
        List<Long> genreIds = getGenreIds(condition);

        List<Long> reviewIds = getReviewIds(condition, pageable, genreIds);

        if (isEmpty(reviewIds)) {
            return new ArrayList<>();
        }

        return queryFactory
                .select(review)
                .from(review)
                .join(review.member, member).fetchJoin()
                .join(review.movie, movie).fetchJoin()
                .where(review.id.in(reviewIds))
                .orderBy(sortByField(condition.getOrderBy(), condition.getOrderDir()))
                .fetch();

    }

    public int getTotalPages(SearchCondition condition) {
        List<Long> genreIds = getGenreIds(condition);

        return queryFactory
                .select(review.count())
                .from(review)
                .join(review.member, member)
                .join(review.movie, movie)
                .where(
                        isTitle(condition.getTitle()),
                        isContent(condition.getContent()),
                        isTerm(condition.getTerm()),
                        isNickname(condition.getNickname()),
                        isMember(condition.getMemberId()),
                        inGenreIds(condition.getMyInterest(), genreIds)
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

    /**
     * private methods
     */

    private List<Long> getGenreIds(SearchCondition condition) {
        return queryFactory
                .select(interest.genre.id)
                .from(interest)
                .join(interest.member, member)
                .where(isMyInterest(condition.getMyInterest()))
                .fetch();
    }

    private List<Long> getReviewIds(SearchCondition condition, Pageable pageable, List<Long> genreIds) {
        return queryFactory
                .select(review.id)
                .from(review)
                .where(
                        isTitle(condition.getTitle()),
                        isContent(condition.getContent()),
                        isTerm(condition.getTerm()),
                        isNickname(condition.getNickname()),
                        isMember(condition.getMemberId()),
                        inGenreIds(condition.getMyInterest(), genreIds)
                )
                .orderBy(sortByField(condition.getOrderBy(), condition.getOrderDir()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression isTitle(String title) {
        return hasText(title) ? review.title.like("%" + title + "%") : null;
    }

    private BooleanExpression isContent(String content) {
        return hasText(content) ? review.content.like("%" + content + "%") : null;
    }

    private BooleanExpression isNickname(String nickname) {
        return hasText(nickname) ? review.member.nickname.like("%" + nickname + "%") : null;
    }

    private BooleanExpression isMember(String memberId) {
        return hasText(memberId) ? review.member.id.eq(Long.parseLong(memberId)) : null;
    }

    private BooleanExpression isTerm(String term) {
        if (hasText(term)) {
            LocalDateTime today = LocalDateTime.now();
            switch (term) {
                case "weekly":
                    return review.createdTime.between(today.minusDays(7), today);
                case "semiannual":
                    return review.createdTime.between(today.minusMonths(6), today);
                case "yearly":
                    return review.createdTime.between(today.minusYears(1), today);
            }
        }
        return null;
    }

    private BooleanExpression inGenreIds(String myInterest, List<Long> genreIds) {
        return hasText(myInterest) ? review.genre.id.in(genreIds) : null;
    }

    private BooleanExpression isMyInterest(String myInterest) {
        return hasText(myInterest) ? interest.member.id.eq(Long.parseLong(myInterest)) : null;
    }

    private OrderSpecifier<?> sortByField(String filedName, String direction) {
        Order order = DESC;
        if (direction.equals("ASC")) {
            order = ASC;
        }

        if (hasText(filedName)) {
            if (filedName.equals("hits")) {
                return new OrderSpecifier<>(order, review.hits);
            }
        }
        return new OrderSpecifier<>(order, review.createdTime);
    }
}
