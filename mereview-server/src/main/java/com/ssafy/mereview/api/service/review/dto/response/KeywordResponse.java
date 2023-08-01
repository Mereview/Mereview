package com.ssafy.mereview.api.service.review.dto.response;

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
}
