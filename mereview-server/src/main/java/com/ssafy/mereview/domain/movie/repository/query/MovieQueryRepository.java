package com.ssafy.mereview.domain.movie.repository.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.mereview.domain.movie.entity.Movie;
import com.ssafy.mereview.domain.review.entity.QReview;
import com.ssafy.mereview.domain.review.entity.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.ssafy.mereview.domain.movie.entity.QMovie.movie;
import static com.ssafy.mereview.domain.review.entity.QReview.review;

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

    public Movie searchById(Long movieId){
        return queryFactory
                .select(movie)
                .from(movie)
                .where(movie.id.eq(movieId))
                .fetchOne();
    }

}
