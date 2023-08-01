package com.ssafy.mereview.domain.review.repository.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchCondition {
    private String title;
    private String content;
    private String orderBy;
    private String term;

    @Builder
    public SearchCondition(String title, String content, String orderBy, String term) {
        this.title = title;
        this.content = content;
        this.orderBy = orderBy;
        this.term = term;
    }
}
