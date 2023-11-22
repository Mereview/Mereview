package com.ssafy.mereview.api.service.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberServiceIntroduceRequest {

    Long id;

    String introduce;

    @Builder
    public MemberServiceIntroduceRequest(Long id, String introduce) {
        this.id = id;
        this.introduce = introduce;
    }
}
