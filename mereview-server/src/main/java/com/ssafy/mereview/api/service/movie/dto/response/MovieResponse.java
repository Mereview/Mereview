package com.ssafy.mereview.api.service.movie.dto.response;

import com.ssafy.mereview.domain.movie.entity.Movie;
import com.ssafy.mereview.domain.movie.entity.MovieGenre;
import com.ssafy.mereview.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor
public class MovieResponse {


    private Long id;

    private int movieContentId;

    private String title;

    private String overview;

    private Double voteAverage;

    private String posterImg;

    private String releaseDate;

    @Builder
    public MovieResponse(Long id, int movieContentId, String title, String overview, Double voteAverage, String posterImg, String releaseDate) {
        this.id = id;
        this.movieContentId = movieContentId;
        this.title = title;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.posterImg = posterImg;
        this.releaseDate = releaseDate;
    }

    public static MovieResponse of(Movie movie){
        return MovieResponse.builder()
                .id(movie.getId())
                .movieContentId(movie.getMovieContentId())
                .title(movie.getTitle())
                .overview(movie.getOverview())
                .voteAverage(movie.getVoteAverage())
                .posterImg(movie.getPosterImg())
                .releaseDate(movie.getReleaseDate())
                .build();
    }
}
