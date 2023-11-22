package com.ssafy.mereview.api.service.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InterestResponse {

    private Long genreId;
    private String genreName;


    @Builder
    public InterestResponse(Long genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }
}
