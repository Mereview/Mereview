package com.ssafy.mereview.api.service.review.dto.response;

import com.ssafy.mereview.api.service.member.dto.response.ProfileImageResponse;
import com.ssafy.mereview.api.service.movie.dto.response.GenreResponse;
import com.ssafy.mereview.domain.review.entity.MovieRecommendType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
public class ReviewResponse {
    private Long reviewId;
    private String reviewTitle;
    private int hits;
    private String highlight;
    private MovieRecommendType movieRecommendType;
    private int commentCount;
    private int funCount;
    private int usefulCount;
    private int badCount;
    private BackgroundImageResponse backgroundImageResponse;
    private LocalDateTime createdTime;
    private Long memberId;
    private String nickname;
    private ProfileImageResponse profileImage;
    private Long movieId;
    private String movieTitle;
    private String movieReleaseDate;
    private GenreResponse genreResponse;

    @Builder
    public ReviewResponse(Long reviewId, String reviewTitle, int hits, String highlight, MovieRecommendType movieRecommendType, int commentCount, int funCount, int usefulCount, int badCount, BackgroundImageResponse backgroundImageResponse, LocalDateTime createdTime, Long memberId, String nickname, ProfileImageResponse profileImage, Long movieId, String movieTitle, String movieReleaseDate, GenreResponse genreResponse) {
        this.reviewId = reviewId;
        this.reviewTitle = reviewTitle;
        this.hits = hits;
        this.highlight = highlight;
        this.movieRecommendType = movieRecommendType;
        this.commentCount = commentCount;
        this.funCount = funCount;
        this.usefulCount = usefulCount;
        this.badCount = badCount;
        this.backgroundImageResponse = backgroundImageResponse;
        this.createdTime = createdTime;
        this.memberId = memberId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieReleaseDate = movieReleaseDate;
        this.genreResponse = genreResponse;
    }
}
