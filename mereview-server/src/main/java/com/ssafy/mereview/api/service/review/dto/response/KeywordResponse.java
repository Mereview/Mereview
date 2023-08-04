package com.ssafy.mereview.api.service.review.dto.response;

import com.ssafy.mereview.domain.review.entity.Keyword;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KeywordResponse {

    private Long keywordId;
    private String keywordName;
    private int keywordWeight;

    @Builder
    public KeywordResponse(Long keywordId, String keywordName, int keywordWeight) {
        this.keywordId = keywordId;
        this.keywordName = keywordName;
        this.keywordWeight = keywordWeight;
    }

    public static KeywordResponse of(Keyword keyword) {
        return KeywordResponse.builder()
                .keywordId(keyword.getId())
                .keywordName(keyword.getName())
                .keywordWeight(keyword.getWeight())
                .build();
    }
}
