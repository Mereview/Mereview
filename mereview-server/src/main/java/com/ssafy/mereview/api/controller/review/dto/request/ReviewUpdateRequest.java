package com.ssafy.mereview.api.controller.review.dto.request;

import com.ssafy.mereview.api.service.review.dto.request.KeywordUpdateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.request.ReviewUpdateServiceRequest;
import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.review.entity.MovieRecommendType;
import com.ssafy.mereview.domain.review.entity.ReviewEvaluationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ReviewUpdateRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String highlight;

    @NotBlank
    private String type;

    @NotEmpty
    private List<KeywordUpdateRequest> keywordRequests;

    @Builder
    public ReviewUpdateRequest(String title, String content, String highlight, String type, List<KeywordUpdateRequest> keywordRequests) {
        this.title = title;
        this.content = content;
        this.highlight = highlight;
        this.type = type;
        this.keywordRequests = keywordRequests;
    }

    public ReviewUpdateServiceRequest toServiceRequest(Long reviewId, UploadFile uploadFile) {
        return ReviewUpdateServiceRequest.builder()
                .reviewId(reviewId)
                .title(title)
                .content(content)
                .highlight(highlight)
                .type(MovieRecommendType.valueOf(type))
                .keywordServiceRequests(toKeywordServiceRequests())
                .uploadFile(uploadFile)
                .build();
    }

    private List<KeywordUpdateServiceRequest> toKeywordServiceRequests() {
        return keywordRequests.stream().map(KeywordUpdateRequest::toServiceRequest).collect(Collectors.toList());
    }
}
