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
    private String name;
    private int weight;

    @Builder
    public KeywordUpdateRequest(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public KeywordUpdateServiceRequest toServiceRequest() {
        return KeywordUpdateServiceRequest.builder()
                .name(name)
                .weight(weight)
                .build();
    }
}
