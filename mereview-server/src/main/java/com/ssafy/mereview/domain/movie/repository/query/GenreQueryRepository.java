package com.ssafy.mereview.domain.movie.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GenreQueryRepository {
    JPAQueryFactory queryFactory;

}
