package com.ssafy.mereview.domain.movie.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.mereview.domain.movie.entity.MovieGenre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.mereview.domain.movie.entity.QMovieGenre.movieGenre;

@Repository
@RequiredArgsConstructor
public class MovieGenreQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<MovieGenre> searchMovieGenreByMovieId(Long movieId) {
        return queryFactory.select(movieGenre)
                .from(movieGenre)
                .where(movieGenre.movie.id.eq(movieId))
                .fetch();
    }
}
