package com.ssafy.mereview.api.controller.review.dto.request;

import com.ssafy.mereview.api.service.review.dto.request.KeywordCreateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.request.ReviewCreateServiceRequest;
import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.review.entity.EvaluationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor
public class ReviewCreateRequest {

    private String title;

    private String content;

    private String highlight;

    private EvaluationType type;

    private Long movieId;

    private List<KeywordCreateRequest> keywordRequests;

    // TODO: 장르 추가해야함


    @Builder
    public ReviewCreateRequest(String title, String content, String highlight, EvaluationType type, Long movieId, List<KeywordCreateRequest> keywordRequests) {
        this.title = title;
        this.content = content;
        this.highlight = highlight;
        this.type = type;
        this.movieId = movieId;
        this.keywordRequests = keywordRequests;
    }

    public ReviewCreateServiceRequest toServiceRequest(UploadFile uploadFile) {
        return ReviewCreateServiceRequest.builder()
                .title(title)
                .content(content)
                .highlight(highlight)
                .type(type)
                .uploadFile(uploadFile)
                .keywordCreateServiceRequests(toKeywordServiceRequests())
                .movieId(movieId)
                .build();
    }

    private List<KeywordCreateServiceRequest> toKeywordServiceRequests() {
        return keywordRequests.stream().map(KeywordCreateRequest::toServiceRequest).collect(Collectors.toList());
    }
}
