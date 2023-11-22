package com.ssafy.mereview.domain.review.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MovieRecommendType {
    YES("추천해요"),
    NO("추천안해요");

    private final String text;
}
