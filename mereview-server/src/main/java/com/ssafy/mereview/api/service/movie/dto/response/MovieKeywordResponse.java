package com.ssafy.mereview.api.service.movie.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class MovieKeywordResponse {

    private String keyword;
    private int score;

    @Builder
    public MovieKeywordResponse(String keyword, int score) {
        this.keyword = keyword;
        this.score = score;
    }

    public static MovieKeywordResponse of(String keyword, int score){
        return MovieKeywordResponse.builder()
                .keyword(keyword)
                .score(score)
                .build();
    }
}
