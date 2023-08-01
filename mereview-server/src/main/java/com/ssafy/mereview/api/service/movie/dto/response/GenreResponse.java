package com.ssafy.mereview.api.service.movie.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GenreResponse {

    private Long genreId;
    private int genreNumber;
    private String genreName;
    private boolean isUsing;

    @Builder
    public GenreResponse(Long genreId, int genreNumber, String genreName, boolean isUsing) {
        this.genreId = genreId;
        this.genreNumber = genreNumber;
        this.genreName = genreName;
        this.isUsing = isUsing;
    }
}
