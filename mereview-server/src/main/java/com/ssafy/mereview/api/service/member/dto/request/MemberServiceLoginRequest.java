package com.ssafy.mereview.api.service.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberServiceLoginRequest {
    private String email;
    private String password;

    @Builder
    public MemberServiceLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
