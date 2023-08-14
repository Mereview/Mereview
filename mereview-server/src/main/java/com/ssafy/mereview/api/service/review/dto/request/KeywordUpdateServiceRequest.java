package com.ssafy.mereview.api.service.review.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KeywordUpdateServiceRequest {

    private Long keywordId;
    private String name;
    private int weight;

    @Builder
    public KeywordUpdateServiceRequest(Long keywordId, String name, int weight) {
        this.keywordId = keywordId;
        this.name = name;
        this.weight = weight;
    }
}
