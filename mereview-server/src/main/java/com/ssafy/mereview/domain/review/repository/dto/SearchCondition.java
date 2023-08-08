package com.ssafy.mereview.domain.review.repository.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SearchCondition {
    private String title;
    private String content;
    private String memberId;
    private String orderBy;
    private String orderDir;
    private String term;

    @Builder
    public SearchCondition(String title, String content, String memberId, String orderBy, String orderDir, String term) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.orderBy = orderBy;
        this.orderDir = orderDir;
        this.term = term;
    }
}
