package com.ssafy.mereview.api.service.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberVerifyRequest {
    private Long id;
    private String password;

    @Builder
    public MemberVerifyRequest(Long id, String password) {
        this.id = id;
        this.password = password;
    }
}
