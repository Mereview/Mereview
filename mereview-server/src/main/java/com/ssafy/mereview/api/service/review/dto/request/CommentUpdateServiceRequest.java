package com.ssafy.mereview.api.service.review.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateServiceRequest {

    private Long commentId;
    private String content;

    @Builder
    public CommentUpdateServiceRequest(Long commentId, String content) {
        this.commentId = commentId;
        this.content = content;
    }
}
