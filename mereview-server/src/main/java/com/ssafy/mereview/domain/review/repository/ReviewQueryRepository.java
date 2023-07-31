package com.ssafy.mereview.domain.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReviewQueryRepository {
    private final JPAQueryFactory queryFactory;
}
