package com.ssafy.mereview.api.controller.review.dto.request;

import com.ssafy.mereview.api.service.review.dto.request.CommentUpdateServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CommentUpdateRequest {

    @NotBlank
    private String content;

    @Builder
    public CommentUpdateRequest(String content) {
        this.content = content;
    }

    public CommentUpdateServiceRequest toServiceRequest(Long commentId) {
        return CommentUpdateServiceRequest.builder().commentId(commentId).content(content).build();
    }
}
