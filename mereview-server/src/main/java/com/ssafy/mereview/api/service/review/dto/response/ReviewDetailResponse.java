package com.ssafy.mereview.api.service.review.dto.response;

import com.ssafy.mereview.api.service.member.dto.response.MemberTierResponse;
import com.ssafy.mereview.api.service.member.dto.response.ProfileImageResponse;
import com.ssafy.mereview.api.service.movie.dto.response.GenreResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor
public class ReviewDetailResponse {

    private Long reviewId;
    private String reviewTitle;
    private String reviewContent;
    private String movieEvaluatedType;
    private int hits;
    private BackgroundImageResponse backgroundImage;
    private String reviewHighlight;
    private LocalDateTime createdTime;
    private List<KeywordResponse> keywords;
    private boolean isEvaluated;
    private int positiveCount;
    private int funCount;
    private int usefulCount;
    private int badCount;
    private Long movieId;
    private String movieTitle;
    private GenreResponse genre;
    private String movieReleaseDate;
    private Long memberId;
    private String nickname;
    private ProfileImageResponse profileImage;
    private List<MemberTierResponse> memberTiers;
    private List<CommentResponse> comments = new ArrayList<>();

    @Builder
    public ReviewDetailResponse(Long reviewId, String reviewTitle, String reviewContent, String movieEvaluatedType, int hits, BackgroundImageResponse backgroundImage, String reviewHighlight, LocalDateTime createdTime, List<KeywordResponse> keywords, boolean isEvaluated, int positiveCount, int funCount, int usefulCount, int badCount, Long movieId, String movieTitle, GenreResponse genre, String movieReleaseDate, Long memberId, String nickname, ProfileImageResponse profileImage, List<MemberTierResponse> memberTiers, List<CommentResponse> comments) {
        this.reviewId = reviewId;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.movieEvaluatedType = movieEvaluatedType;
        this.hits = hits;
        this.backgroundImage = backgroundImage;
        this.reviewHighlight = reviewHighlight;
        this.createdTime = createdTime;
        this.keywords = keywords;
        this.isEvaluated = isEvaluated;
        this.positiveCount = positiveCount;
        this.funCount = funCount;
        this.usefulCount = usefulCount;
        this.badCount = badCount;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.genre = genre;
        this.movieReleaseDate = movieReleaseDate;
        this.memberId = memberId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.memberTiers = memberTiers;
        this.comments = comments;
    }
}
