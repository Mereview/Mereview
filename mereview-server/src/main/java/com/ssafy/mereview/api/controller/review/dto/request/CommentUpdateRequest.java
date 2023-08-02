package com.ssafy.mereview.api.controller.review.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateRequest {
    private String content;

    @Builder
    public CommentUpdateRequest(String content) {
        this.content = content;
    }

    public CommentUpdateServiceRequest toServiceRequest() {
        return CommentUpdateServiceRequest.builder().content(content).build();
    }
}
