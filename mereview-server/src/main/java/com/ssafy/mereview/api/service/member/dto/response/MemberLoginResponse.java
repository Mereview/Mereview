package com.ssafy.mereview.api.service.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class MemberLoginResponse {
    private Long id;

    private String email;

    private ProfileImageResponse profileImage;

    private String nickname;

    private String role;

    private String accessToken;

    private String refreshToken;
    @Builder
    public MemberLoginResponse(Long id, String email, ProfileImageResponse profileImage, String nickname, String role, String accessToken, String refreshToken) {
        this.id = id;
        this.email = email;
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.role = role;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
