package com.ssafy.mereview.api.controller.review.dto.request;

import com.ssafy.mereview.api.service.review.dto.request.KeywordUpdateServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class KeywordUpdateRequest {
    @NotBlank
    private Long keywordId;
    @NotBlank
    private String name;
    private int weight;

    @Builder
    public KeywordUpdateRequest(Long keywordId, String name, int weight) {
        this.keywordId = keywordId;
        this.name = name;
        this.weight = weight;
    }

    public KeywordUpdateServiceRequest toServiceRequest() {
        return KeywordUpdateServiceRequest.builder()
                .keywordId(keywordId)
                .name(name)
                .weight(weight)
                .build();
    }
}
