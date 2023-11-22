package com.ssafy.mereview.api.service.member.dto.response;

import com.ssafy.mereview.domain.member.entity.MemberFollow;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowerResponse {

    Long id;

    String nickname;


    @Builder
    public FollowerResponse(Long id, String nickname, ProfileImageResponse profileImage) {
        this.id = id;
        this.nickname = nickname;
    }

    public static FollowerResponse of(MemberFollow memberFollow) {
        return FollowerResponse.builder()
                .id(memberFollow.getMember().getId())
                .nickname(memberFollow.getMember().getNickname())
                .build();
    }
}
