package com.ssafy.mereview.api.service.member.dto.response;

import com.ssafy.mereview.domain.member.entity.Rank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberAchievementResponse {
    private String genreName;

    private String achievementName;

    private Rank achievementRank;

    @Builder
    public MemberAchievementResponse(String genreName, String achievementName, Rank achievementRank) {
        this.genreName = genreName;
        this.achievementName = achievementName;
        this.achievementRank = achievementRank;
    }
}
