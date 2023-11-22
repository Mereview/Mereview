package com.ssafy.mereview.domain.movie.repository.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.mereview.common.util.SizeConstants;
import com.ssafy.mereview.domain.movie.entity.Movie;
import com.ssafy.mereview.domain.review.entity.QReview;
import com.ssafy.mereview.domain.review.entity.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.ssafy.mereview.common.util.SizeConstants.MOVIE_SIZE;
import static com.ssafy.mereview.common.util.SizeConstants.PAGE_SIZE;
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
                .where(movie.title.startsWith(keyword))
                .orderBy(movie.title.asc())
                .limit(MOVIE_SIZE)
                .fetch();
    }

    public Movie searchById(Long movieId){
        return queryFactory
                .select(movie)
                .from(movie)
                .where(movie.id.eq(movieId))
                .fetchOne();
    }

    public Long searchMovieIdByContentId(Integer contentId) {
        return queryFactory.select(movie.id)
                .from(movie)
                .where(movie.movieContentId.eq(contentId))
                .fetchOne();
    }
}
