package com.ssafy.mereview.api.service.member.dto.response;

import com.ssafy.mereview.api.service.member.dto.response.InterestResponse;
import com.ssafy.mereview.api.service.member.dto.response.MemberAchievementResponse;
import com.ssafy.mereview.api.service.member.dto.response.MemberTierResponse;
import com.ssafy.mereview.domain.member.entity.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

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

    @Builder

    public MemberResponse(Long id, String email, String nickname, String gender, String birthDate, Role role, List<InterestResponse> interests, List<MemberTierResponse> tiers, List<MemberAchievementResponse> achievements) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.role = role;
        this.interests = interests;
        this.tiers = tiers;
        this.achievements = achievements;
    }
}
