package com.ssafy.mereview.domain.movie.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.mereview.domain.movie.entity.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.mereview.domain.movie.entity.QMovie.movie;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MovieQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Movie> searchMovieByKeyword(String keyword) {
        log.debug("keyword in Repository = {}", keyword);
        return queryFactory
                .selectFrom(movie)
                .where(movie.title.contains(keyword))
                .limit(10)
                .fetch();
    }
}
