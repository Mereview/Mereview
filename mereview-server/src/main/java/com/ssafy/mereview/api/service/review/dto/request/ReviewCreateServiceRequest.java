package com.ssafy.mereview.api.service.review.dto.request;

import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.movie.entity.Genre;
import com.ssafy.mereview.domain.movie.entity.Movie;
import com.ssafy.mereview.domain.review.entity.MovieRecommendType;
import com.ssafy.mereview.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewCreateServiceRequest {

    private String title;
    private String content;
    private String highlight;
    private MovieRecommendType type;
    private Long memberId;
    private Long movieId;
    private Long genreId;
    private List<KeywordCreateServiceRequest> keywordServiceRequests;
    private UploadFile uploadFile;

    @Builder
    public ReviewCreateServiceRequest(String title, String content, String highlight, MovieRecommendType type, Long memberId, Long movieId, Long genreId, List<KeywordCreateServiceRequest> keywordCreateServiceRequests, UploadFile uploadFile) {
        this.title = title;
        this.content = content;
        this.highlight = highlight;
        this.type = type;
        this.memberId = memberId;
        this.movieId = movieId;
        this.genreId = genreId;
        this.keywordServiceRequests = keywordCreateServiceRequests;
        this.uploadFile = uploadFile;
    }

    public Review toEntity() {
        return Review.builder()
                .title(title)
                .content(content)
                .highlight(highlight)
                .type(type)
                .genre(Genre.builder().id(genreId).build())
                .member(Member.builder().id(memberId).build())
                .movie(Movie.builder().id(movieId).build())
                .build();
    }
}
