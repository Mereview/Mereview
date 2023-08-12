package com.ssafy.mereview.api.service.member.dto.response;

import com.ssafy.mereview.api.service.review.dto.response.ReviewResponse;
import com.ssafy.mereview.domain.member.entity.Role;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class MemberResponse {

    private Long id;
    private String email;
    private String nickname;
    private String gender;
    private String birthDate;
    private Role role;
    private LocalDateTime createdTime;
    private String introduce;
    private List<InterestResponse> interests;
    private List<MemberTierResponse> tiers;
    private List<MemberAchievementResponse> achievements;
    private int reviews;
    private int following;
    private int follower;
    private ProfileImageResponse profileImage;
    private int todayVisitCount;
    private int totalVisitCount;

    @Builder
    public MemberResponse(Long id, String email, String nickname, String gender, String birthDate, Role role, LocalDateTime createdTime, String introduce, List<InterestResponse> interests, List<MemberTierResponse> tiers, List<MemberAchievementResponse> achievements, int reviews, int following, int follower, ProfileImageResponse profileImage, int todayVisitCount, int totalVisitCount) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.role = role;
        this.createdTime = createdTime;
        this.introduce = introduce;
        this.interests = interests;
        this.tiers = tiers;
        this.achievements = achievements;
        this.reviews = reviews;
        this.following = following;
        this.follower = follower;
        this.profileImage = profileImage;
        this.todayVisitCount = todayVisitCount;
        this.totalVisitCount = totalVisitCount;
    }
}
