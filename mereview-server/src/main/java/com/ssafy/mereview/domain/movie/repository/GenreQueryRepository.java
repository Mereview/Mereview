package com.ssafy.mereview.domain.movie.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GenreQueryRepository {
    JPAQueryFactory queryFactory;

}
