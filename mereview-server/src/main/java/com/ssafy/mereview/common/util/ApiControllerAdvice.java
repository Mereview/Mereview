package com.ssafy.mereview.common.util;

import com.ssafy.mereview.common.response.ApiResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 정해줘야함
    @ExceptionHandler({BindException.class, NoSuchElementException.class, DuplicateKeyException.class, IllegalArgumentException.class})  // 처리할 예외 클래스
    public ApiResponse<Object> bindException(Exception exception) {
        return ApiResponse.of(HttpStatus.BAD_REQUEST, exception);
    }
}
