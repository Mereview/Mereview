package com.ssafy.mereview.api.service.review.dto.request;

import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.review.entity.EvaluationType;
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

    private EvaluationType type;

    private Long movieId;

    private List<KeywordCreateServiceRequest> keywordServiceRequests;

    private UploadFile uploadFile;

    @Builder
    public ReviewCreateServiceRequest(String title, String content, String highlight, EvaluationType type, Long movieId, List<KeywordCreateServiceRequest> keywordCreateServiceRequests, UploadFile uploadFile) {
        this.title = title;
        this.content = content;
        this.highlight = highlight;
        this.type = type;
        this.movieId = movieId;
        this.keywordServiceRequests = keywordCreateServiceRequests;
        this.uploadFile = uploadFile;
    }

    public Review toEntity() {
        return Review.builder()
                .title(this.title)
                .content(this.content)
                .highlight(this.highlight)
                .type(this.type)
//                .member(Member.builder().id(this.memberId).build())
//                .movie(Movie.builder().id(this.movieId).build());
                .build();
    }
}
