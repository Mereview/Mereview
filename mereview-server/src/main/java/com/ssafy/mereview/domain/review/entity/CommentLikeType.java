package com.ssafy.mereview.domain.review.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommentLikeType {
    LIKE("좋아요"),
    DISLIKE("싫어요");

    private final String text;
}
