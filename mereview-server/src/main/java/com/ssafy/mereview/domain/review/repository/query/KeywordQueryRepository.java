package com.ssafy.mereview.domain.review.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.mereview.domain.review.entity.Keyword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class KeywordQueryRepository {

    private final JPAQueryFactory queryFactory;


}
