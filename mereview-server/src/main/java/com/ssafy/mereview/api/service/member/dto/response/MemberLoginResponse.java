package com.ssafy.mereview.api.service.member.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class MemberLoginResponse {
    MemberResponse memberResponse;

    Map<String, String> token;

    @Builder
    public MemberLoginResponse(MemberResponse memberResponse, Map<String, String> token) {
        this.memberResponse = memberResponse;
        this.token = token;
    }
}
