package com.ssafy.mereview.api.controller.member.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InterestRequest {

    private Long genreId;

    private String genreName;

    @Builder
    public InterestRequest(Long genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }
}
