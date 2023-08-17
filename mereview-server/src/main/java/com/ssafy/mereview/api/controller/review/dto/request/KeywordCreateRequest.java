package com.ssafy.mereview.api.controller.review.dto.request;

import com.ssafy.mereview.api.service.review.dto.request.KeywordCreateServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class KeywordCreateRequest {

    @NotBlank
    private String name;
    @NotNull
    private Integer weight;
    @NotNull
    private Long movieId;

    @Builder
    public KeywordCreateRequest(String name, Integer weight, Long movieId) {
        this.name = name;
        this.weight = weight;
        this.movieId = movieId;
    }

    public KeywordCreateServiceRequest toServiceRequest() {
        return KeywordCreateServiceRequest.builder()
                .name(name)
                .weight(weight)
                .movieId(movieId)
                .build();
    }
}
