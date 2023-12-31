package com.ssafy.mereview.api.controller.review.dto.request;

import com.ssafy.mereview.api.service.review.dto.request.CommentCreateServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CommentCreateRequest {

    @NotBlank
    private String content;
    @NotNull
    private Long reviewId;
    @NotNull
    private Long memberId;
    @NotNull
    private Long genreId;


    @Builder
    public CommentCreateRequest(String content, Long reviewId, Long memberId, Long genreId) {
        this.content = content;
        this.reviewId = reviewId;
        this.memberId = memberId;
        this.genreId = genreId;
    }

    public CommentCreateServiceRequest toServiceRequest() {
        return CommentCreateServiceRequest.builder()
                .reviewId(reviewId)
                .memberId(memberId)
                .content(content)
                .genreId(genreId)
                .build();
    }
}
