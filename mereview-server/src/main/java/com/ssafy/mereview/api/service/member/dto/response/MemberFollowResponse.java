package com.ssafy.mereview.api.service.member.dto.response;

import com.ssafy.mereview.domain.member.entity.MemberFollow;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberFollowResponse {
    Long memberId;

    Long targetId;

    String status;

    @Builder
    public MemberFollowResponse(Long memberId, Long targetId, String status) {
        this.memberId = memberId;
        this.targetId = targetId;
        this.status = status;
    }

    public static MemberFollowResponse of(MemberFollow memberFollow, String status){
        return MemberFollowResponse.builder()
                .memberId(memberFollow.getMember().getId())
                .targetId(memberFollow.getTargetMember().getId())
                .status(status)
                .build();
    }
}
