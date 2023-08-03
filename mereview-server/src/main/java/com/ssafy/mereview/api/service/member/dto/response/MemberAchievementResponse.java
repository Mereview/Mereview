package com.ssafy.mereview.api.service.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberAchievementResponse {
    private String genreName;

    private String achievementName;

    private String achievementRank;

    @Builder
    public MemberAchievementResponse(String genreName, String achievementName, String achievementRank) {
        this.genreName = genreName;
        this.achievementName = achievementName;
        this.achievementRank = achievementRank;
    }
}
