package com.ssafy.mereview.api.service.movie.dto.response;

import com.ssafy.mereview.domain.movie.entity.Genre;
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

    public static GenreResponse of(Genre genre) {
        return GenreResponse.builder()
                .genreId(genre.getId())
                .genreNumber(genre.getGenreNumber())
                .genreName(genre.getGenreName())
                .isUsing(genre.isUsing())
                .build();
    }
}
