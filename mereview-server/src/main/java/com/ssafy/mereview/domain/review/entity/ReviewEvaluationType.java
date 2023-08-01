package com.ssafy.mereview.domain.review.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewEvaluationType {
    FUN("재밌어요"),
    USEFUL("유용해요"),
    BAD("별로에요");

    private final String text;
}
