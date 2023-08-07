package com.ssafy.mereview.domain.review.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationStatus {

    CONFIRMED("확인함"),
    UNCONFIRMED("확인안함");

    private final String text;
}
