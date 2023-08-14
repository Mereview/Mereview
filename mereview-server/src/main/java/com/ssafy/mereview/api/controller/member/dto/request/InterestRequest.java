package com.ssafy.mereview.api.controller.member.dto.request;

import com.ssafy.mereview.api.service.member.dto.request.InterestServiceRequest;
import com.ssafy.mereview.domain.member.entity.Interest;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.movie.entity.Genre;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InterestRequest {

    private Long genreId;

    private int genreNumber;

    private String genreName;

    private boolean isUsing;

    @Builder
    public InterestRequest(Long genreId, int genreNumber, String genreName, boolean isUsing) {
        this.genreId = genreId;
        this.genreNumber = genreNumber;
        this.genreName = genreName;
        this.isUsing = isUsing;
    }

    public InterestServiceRequest toServiceRequest() {
        return InterestServiceRequest.builder()
                .genreId(genreId)
                .genreNumber(genreNumber)
                .genreName(genreName)
                .isUsing(isUsing)
                .build();

    }
}
