package com.ssafy.mereview.api.service.member.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InterestResponse {


    private String genreName;

    @Builder
    public InterestResponse(String genreName) {
        this.genreName = genreName;
    }
}
