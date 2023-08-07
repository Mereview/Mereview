package com.ssafy.mereview.api.service.member.dto.response;

import com.ssafy.mereview.domain.member.entity.Member;
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

    public static FollowResponse of(Member member) {
        return FollowResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .profileImage(ProfileImageResponse.of(member.getProfileImage()))
                .build();
    }
}
