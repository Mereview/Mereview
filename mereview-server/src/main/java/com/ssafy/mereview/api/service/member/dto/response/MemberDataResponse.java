package com.ssafy.mereview.api.service.member.dto.response;

import com.ssafy.mereview.api.service.review.dto.response.NotificationResponse;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.entity.ProfileImage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberDataResponse {

    private Long id;
    private String nickname;
    private ProfileImageResponse profileImage;
    private String role;
    private List<NotificationResponse> notifications;
    private int notificationCount;


    @Builder
    public MemberDataResponse(Long id, String nickname, ProfileImageResponse profileImage, String role, List<NotificationResponse> notifications, int notificationCount) {
        this.id = id;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.role = role;
        this.notifications = notifications;
        this.notificationCount = notificationCount;
    }

    public static MemberDataResponse of(Member member, List<NotificationResponse> notificationResponses, int count) {
        ProfileImage profileImage = member.getProfileImage();
        return MemberDataResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .notificationCount(count)
                .notifications(notificationResponses)
                .profileImage(ProfileImageResponse.of(member.getProfileImage()))
                .role(member.getRole().toString())
                .build();
    }
}
