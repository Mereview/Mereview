package com.ssafy.mereview.api.service.review.dto.response;

import com.ssafy.mereview.api.service.member.dto.response.MemberTierResponse;
import com.ssafy.mereview.api.service.movie.dto.response.GenreResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewDetailResponse {

    private Long reviewId;
    private String reviewTitle;
    private String reviewContent;
    private int hits;
    private BackgroundImageResponse backgroundImage;
    private String reviewHighlight;
    private LocalDateTime reviewCreatedTime;
    private List<KeywordResponse> keywords;
    private List<ReviewEvaluationResponse> reviewEvaluations;
    private Long movieId;
    private String movieTitle;
    // TODO: 2023-08-01 ids 로 할지 genreResponses 로 할지 결정
    private GenreResponse genreResponse;
    private String movieReleaseDate;
    private Long memberId;
    private String nickname;
    // 프로필
    private List<MemberTierResponse> memberTiers;
    // 댓글

    @Builder
    public ReviewDetailResponse(Long reviewId, String reviewTitle, String reviewContent, int hits, BackgroundImageResponse backgroundImage, String reviewHighlight, LocalDateTime reviewCreatedTime, List<KeywordResponse> keywords, List<ReviewEvaluationResponse> reviewEvaluations, Long movieId, String movieTitle, GenreResponse genreResponse, String movieReleaseDate, Long memberId, String nickname, List<MemberTierResponse> memberTiers) {
        this.reviewId = reviewId;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.hits = hits;
        this.backgroundImage = backgroundImage;
        this.reviewHighlight = reviewHighlight;
        this.reviewCreatedTime = reviewCreatedTime;
        this.keywords = keywords;
        this.reviewEvaluations = reviewEvaluations;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.genreResponse = genreResponse;
        this.movieReleaseDate = movieReleaseDate;
        this.memberId = memberId;
        this.nickname = nickname;
        this.memberTiers = memberTiers;
    }
}
