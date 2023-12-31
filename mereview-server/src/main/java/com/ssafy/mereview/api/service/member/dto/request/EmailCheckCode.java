package com.ssafy.mereview.api.service.member.dto.request;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailCheckCode {

    private String verificationCode;

    private String jwtToken;

    @Builder
    public EmailCheckCode(String verificationCode, String jwtToken) {
        this.verificationCode = verificationCode;
        this.jwtToken = jwtToken;
    }
}
