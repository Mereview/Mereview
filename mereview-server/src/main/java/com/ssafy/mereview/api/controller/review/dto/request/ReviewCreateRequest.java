package com.ssafy.mereview.api.controller.review.dto.request;

import com.ssafy.mereview.api.service.review.dto.request.KeywordCreateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.request.ReviewCreateServiceRequest;
import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.review.entity.EvaluationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor
public class ReviewCreateRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String highlight;

    @NotNull
    private EvaluationType type;

    @NotNull
    private Long memberId;

    @NotNull
    private Long movieId;

    @NotNull
    private Long genreId;

    @NotEmpty
    private List<KeywordCreateRequest> keywordRequests;

    @Builder
    public ReviewCreateRequest(String title, String content, String highlight, EvaluationType type, Long memberId, Long movieId, Long genreId, List<KeywordCreateRequest> keywordRequests) {
        this.title = title;
        this.content = content;
        this.highlight = highlight;
        this.type = type;
        this.memberId = memberId;
        this.movieId = movieId;
        this.genreId = genreId;
        this.keywordRequests = keywordRequests;
    }

    public ReviewCreateServiceRequest toServiceRequest(UploadFile uploadFile) {
        return ReviewCreateServiceRequest.builder()
                .title(title)
                .content(content)
                .highlight(highlight)
                .type(type)
                .memberId(memberId)
                .movieId(movieId)
                .genreId(genreId)
                .keywordCreateServiceRequests(toKeywordServiceRequests())
                .uploadFile(uploadFile)
                .build();
    }

    private List<KeywordCreateServiceRequest> toKeywordServiceRequests() {
        return keywordRequests.stream().map(KeywordCreateRequest::toServiceRequest).collect(Collectors.toList());
    }
}
