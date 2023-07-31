package com.ssafy.mereview.api.controller.member.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InterestRequest {

    private String genreName;

    @Builder
    public InterestRequest(String genreName) {
        this.genreName = genreName;
    }
}
