package com.ssafy.mereview.api.service.member.dto.response;

import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.entity.MemberFollow;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowResponse {

    Long id;

    String nickname;

    private ProfileImageResponse profileImage;

    @Builder
    public FollowResponse(Long id, String nickname, ProfileImageResponse profileImage) {
        this.id = id;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public static FollowResponse of(MemberFollow memberFollow) {
        return FollowResponse.builder()
                .id(memberFollow.getId())
                .nickname(memberFollow.getMember().getNickname())
                .profileImage(ProfileImageResponse.of(memberFollow.getMember().getProfileImage()))
                .build();
    }
}
