package com.ssafy.mereview.api.service.member.dto.response;

import com.ssafy.mereview.domain.member.entity.MemberFollow;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowingResponse {

    Long id;

    String nickname;


    @Builder
    public FollowingResponse(Long id, String nickname, ProfileImageResponse profileImage) {
        this.id = id;
        this.nickname = nickname;
    }

    public static FollowingResponse of(MemberFollow memberFollow) {
        return FollowingResponse.builder()
                .id(memberFollow.getTargetMember().getId())
                .nickname(memberFollow.getMember().getNickname())
                .build();
    }
}
