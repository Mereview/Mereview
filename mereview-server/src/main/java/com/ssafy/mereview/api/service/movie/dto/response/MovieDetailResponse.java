package com.ssafy.mereview.api.service.movie.dto.response;

import com.ssafy.mereview.api.service.review.dto.response.ReviewEvaluationResponse;
import com.ssafy.mereview.api.service.review.dto.response.ReviewResponse;
import com.ssafy.mereview.domain.movie.entity.Movie;
import com.ssafy.mereview.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
public class MovieDetailResponse {

    //movie
    private String title;
    private String overview;
    private Double voteAverage;
    private String posterImg;
    private String releaseDate;

    //evaluation
    private Double evaluation;

    //keyword
    private List<MovieKeywordResponse> movieKeyword;

    //review
    private List<ReviewResponse> topReviews;
    private List<ReviewResponse> recentReviews;

    @Builder
    public MovieDetailResponse(String title, String overview, Double voteAverage, String posterImg, String releaseDate, Double evaluation, List<MovieKeywordResponse> movieKeyword, List<ReviewResponse> topReviews, List<ReviewResponse> recentReviews) {
        this.title = title;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.posterImg = posterImg;
        this.releaseDate = releaseDate;
        this.evaluation = evaluation;
        this.movieKeyword = movieKeyword;
        this.topReviews = topReviews;
        this.recentReviews = recentReviews;
    }

    public static MovieDetailResponse of(Movie movie, Double evaluation, List<MovieKeywordResponse> movieKeywordResponses, List<ReviewResponse> topReviewResponses, List<ReviewResponse> recentReviewResponses) {
        return MovieDetailResponse.builder()
                .title(movie.getTitle())
                .overview(movie.getOverview())
                .voteAverage(movie.getVoteAverage())
                .posterImg(movie.getPosterImg())
                .releaseDate(movie.getReleaseDate())
                .evaluation(evaluation)
                .movieKeyword(movieKeywordResponses)
                .topReviews(topReviewResponses)
                .recentReviews(recentReviewResponses)
                .build();
    }

}
