package com.ssafy.mereview.api.service.member.dto.response;


import com.ssafy.mereview.api.service.review.dto.response.ReviewResponse;
import com.ssafy.mereview.domain.member.entity.Role;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class MemberResponse {

    private Long id;
    private String email;
    private String nickname;
    private String gender;
    private String birthDate;
    private Role role;
    private List<InterestResponse> interests;
    private List<MemberTierResponse> tiers;
    private List<MemberAchievementResponse> achievements;
    private List<ReviewResponse> reviews;
    private ProfileImageResponse profileImage;


    @Builder
    public MemberResponse(Long id, String email, String nickname, String gender, String birthDate, Role role, List<InterestResponse> interests, List<MemberTierResponse> tiers, List<MemberAchievementResponse> achievements, List<ReviewResponse> reviews, ProfileImageResponse profileImage, Map<String, String> token) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.role = role;
        this.interests = interests;
        this.tiers = tiers;
        this.achievements = achievements;
        this.reviews = reviews;
        this.profileImage = profileImage;
    }
}
