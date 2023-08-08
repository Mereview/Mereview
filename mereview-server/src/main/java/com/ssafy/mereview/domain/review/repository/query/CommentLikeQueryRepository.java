package com.ssafy.mereview.domain.review.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.mereview.domain.review.entity.CommentLike;
import com.ssafy.mereview.domain.review.entity.CommentLikeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ssafy.mereview.domain.review.entity.QCommentLike.commentLike;

@RequiredArgsConstructor
@Repository
public class CommentLikeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<CommentLike> searchByCommentAndMember(Long commentId, Long memberId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(commentLike)
                        .where(
                                commentLike.comment.id.eq(commentId),
                                commentLike.member.id.eq(memberId)
                        )
                        .fetchOne());
    }

    public Map<CommentLikeType, Integer> getCountByCommentIdGroupByType(Long commentId) {
        return queryFactory
                .select(commentLike.type, commentLike.count())
                .from(commentLike)
                .where(commentLike.comment.id.eq(commentId))
                .groupBy(commentLike.type)
                .stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(commentLike.type),
                        tuple -> Objects.requireNonNull(tuple.get(commentLike.count())).intValue()
                ));
    }

}
