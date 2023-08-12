package com.ssafy.mereview.domain.review.repository.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchCondition {
    private String title;
    private String content;
    private String memberId;
    private String myInterest;
    private String nickname;
    private String orderBy;
    private String orderDir;
    private String term;
    private String genreId;

    @Builder
    public SearchCondition(String title, String content, String memberId, String myInterest, String nickname, String orderBy, String orderDir, String term, String genreId) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.myInterest = myInterest;
        this.nickname = nickname;
        this.orderBy = orderBy;
        this.orderDir = orderDir;
        this.term = term;
        this.genreId = genreId;
    }
}
