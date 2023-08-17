package com.ssafy.mereview.api.service.review.dto.request;

import com.ssafy.mereview.domain.movie.entity.Movie;
import com.ssafy.mereview.domain.review.entity.Keyword;
import com.ssafy.mereview.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KeywordUpdateServiceRequest {

    private String name;
    private Integer weight;
    private Long movieId;

    @Builder
    public KeywordUpdateServiceRequest(String name, int weight, Long movieId) {
        this.name = name;
        this.weight = weight;
        this.movieId = movieId;
    }

    public Keyword toEntity(Long reviewId) {
        return Keyword.builder()
                .name(name)
                .weight(weight)
                .review(Review.builder().id(reviewId).build())
                .movie(Movie.builder().id(movieId).build())
                .build();
    }
}
