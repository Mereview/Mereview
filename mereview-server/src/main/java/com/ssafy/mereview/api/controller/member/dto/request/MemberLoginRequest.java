package com.ssafy.mereview.api.controller.member.dto.request;

import com.ssafy.mereview.api.service.member.dto.request.MemberServiceLoginRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberLoginRequest {

    private String email;

    private String password;

    @Builder
    public MemberLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public MemberServiceLoginRequest toServiceRequest() {
        return MemberServiceLoginRequest.builder()
                .email(email)
                .password(password)
                .build();
    }
}
