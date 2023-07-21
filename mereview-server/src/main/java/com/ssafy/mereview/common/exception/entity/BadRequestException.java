package com.ssafy.mereview.common.exception.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BadRequestException extends RuntimeException {
    private final ErrorCode errorCode;

}
