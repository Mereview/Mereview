package com.ssafy.mereview.common.exception.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /*
     * 400 BAD_REQUEST: 잘못된 요청
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    /*
     * 400 BAD_REQUEST: 잘못된 요청
     */
    BAD_REQUEST_SAME_PWD_TWICE(HttpStatus.BAD_REQUEST, "같은 비밀번호를 입력 했습니다."),

    /*
     * 404 NOT_FOUND: 리소스를 찾을 수 없음
     */
    POSTS_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 글입니다."),

    /*
     * 405 METHOD_NOT_ALLOWED: 허용되지 않은 Request Method 호출
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다."),

    /*
     * 500 INTERNAL_SERVER_ERROR: 내부 서버 오류
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다."),

    /*
     * 401 UNAUTHORIZED_ERROR : 계정권한이 유효하지 않습니다.
     */
    UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED, "계정권한이 유효하지 않습니다."),

    /*
     * 401 UNAUTHORIZED_ERROR : 계정권한이 유효하지 않습니다.
     */
    PASSWORD_UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),

    /*
     * 401 UNAUTHORIZED_ERROR : 계정권한이 유효하지 않습니다.
     */
    AT_UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED, "access-token이 유효하지 않습니다."),

    /*
     * 401 UNAUTHORIZED_ERROR : 계정권한이 유효하지 않습니다.
     */
    RT_UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED, "refresh-token이 유효하지 않습니다."),

    /*
     * 401 UNAUTHORIZED_ERROR : 계정권한이 유효하지 않습니다.
     */
    LOGIN_UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED, "로그인정보가 유효하지 않습니다."),

    /*
     * 409 CONFLICT_ERROR : 중복된 아이디입니다.
     */
    LOGIN_ID_CONFLICT_ERROR(HttpStatus.CONFLICT, "중복된 아이디입니다."),

    /*
     * 409 CONFLICT_ERROR : 중복된 이메일입니다.
     */
    EMAIL_CONFLICT_ERROR(HttpStatus.CONFLICT, "중복된 이메일입니다.")
    ;

    private final HttpStatus status;
    private final String message;

}