package com.ssafy.mereview.api.controller.member.dto.request;

import com.ssafy.mereview.api.service.member.dto.request.MemberServiceIntroduceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberIntroduceRequest {

    Long id;

    String introduce;

    @Builder
    public MemberIntroduceRequest(Long id, String introduce) {
        this.id = id;
        this.introduce = introduce;
    }

    public MemberServiceIntroduceRequest toServiceRequest(){
        return MemberServiceIntroduceRequest.builder()
                .id(this.id)
                .introduce(this.introduce)
                .build();
    }
}
