package com.ssafy.mereview.api.service.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberNicknameUpdateServiceRequest {

    Long id;

    String nickname;

    @Builder
    public MemberNicknameUpdateServiceRequest(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}
