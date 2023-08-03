package com.ssafy.mereview.api.service.review.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateServiceRequest {

    private String content;

    @Builder
    public CommentUpdateServiceRequest(String content) {
        this.content = content;
    }
}