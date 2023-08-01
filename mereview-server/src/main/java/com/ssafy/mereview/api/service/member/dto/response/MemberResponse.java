package com.ssafy.mereview.api.service.member.dto.response;


import com.ssafy.mereview.domain.member.entity.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@ToString
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

    private Map<String, String> token;

    @Builder
    public MemberResponse(Long id, String email, String nickname, String gender, String birthDate, Role role, List<InterestResponse> interests, List<MemberTierResponse> tiers, List<MemberAchievementResponse> achievements, Map<String, String> token) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.role = role;
        this.interests = interests;
        this.tiers = tiers;
        this.achievements = achievements;
        this.token = token;
    }
}
