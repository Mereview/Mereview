package com.ssafy.mereview.api.service.review.dto.response;

import com.ssafy.mereview.api.service.movie.dto.response.GenreResponse;
import com.ssafy.mereview.domain.review.entity.EvaluationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor
public class ReviewResponse {
    private Long reviewId;
    private Long memberId;
    private String nickname;
    // TODO: 2023-07-31 회원 프로필 이미지 추가 예정
    private Long movieId;
    private String movieTitle;
    private GenreResponse genreResponse;
    private String movieReleaseDate;
    private String reviewTitle;
    private int hits;
    private String highlight;
    private EvaluationType evaluationType;
    private int commentCount;
    private int funCount;
    private int usefulCount;
    private int badCount;
    private BackgroundImageResponse backgroundImageResponse;
    private LocalDateTime createdTime;

    @Builder
    public ReviewResponse(Long reviewId, Long memberId, String nickname, Long movieId, String movieTitle, GenreResponse genreResponse, String movieReleaseDate, String reviewTitle, int hits, String highlight, EvaluationType evaluationType, int commentCount, int funCount, int usefulCount, int badCount, BackgroundImageResponse backgroundImageResponse, LocalDateTime createdTime) {
        this.reviewId = reviewId;
        this.memberId = memberId;
        this.nickname = nickname;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.genreResponse = genreResponse;
        this.movieReleaseDate = movieReleaseDate;
        this.reviewTitle = reviewTitle;
        this.hits = hits;
        this.highlight = highlight;
        this.evaluationType = evaluationType;
        this.commentCount = commentCount;
        this.funCount = funCount;
        this.usefulCount = usefulCount;
        this.badCount = badCount;
        this.backgroundImageResponse = backgroundImageResponse;
        this.createdTime = createdTime;
    }
}
