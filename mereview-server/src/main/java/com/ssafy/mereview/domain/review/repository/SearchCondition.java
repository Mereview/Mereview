package com.ssafy.mereview.domain.review.repository;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchCondition {
    private String title;
    private String content;

    @Builder
    public SearchCondition(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
