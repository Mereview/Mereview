package com.ssafy.mereview.api.controller.member.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InterestRequest {

    private String genreId;

    private String genreName;

    @Builder
    public InterestRequest(String genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }
}
