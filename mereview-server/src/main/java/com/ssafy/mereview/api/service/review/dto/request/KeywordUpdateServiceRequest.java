package com.ssafy.mereview.api.service.review.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KeywordUpdateServiceRequest {

    private String name;
    private int weight;

    @Builder
    public KeywordUpdateServiceRequest(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }
}
