package com.ssafy.mereview.api.controller.review.dto.request;

import com.ssafy.mereview.api.service.review.dto.request.CommentLikeServiceRequest;
import com.ssafy.mereview.domain.review.entity.CommentLikeType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CommentLikeRequest {

    @NotNull
    private Long commentId;
    @NotNull
    private Long memberId;
    @NotBlank
    private CommentLikeType type;

    @Builder
    public CommentLikeRequest(Long commentId, Long memberId, CommentLikeType type) {
        this.commentId = commentId;
        this.memberId = memberId;
        this.type = type;
    }

    public CommentLikeServiceRequest toServiceRequest() {
        return CommentLikeServiceRequest.builder()
                .commentId(commentId)
                .memberId(memberId)
                .type(type)
                .build();
    }
}
