package com.ssafy.mereview.api.service.member.dto.response;

import com.ssafy.mereview.domain.member.entity.AchievementType;
import com.ssafy.mereview.domain.member.entity.MemberAchievement;
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

    private double achievementPercent;

    private int achievementCount;

    private AchievementType achievementType;

    @Builder
    public MemberAchievementResponse(String genreName, String achievementName, Rank achievementRank, int achievementCount, AchievementType achievementType) {
        this.genreName = genreName;
        this.achievementName = achievementName;
        this.achievementRank = achievementRank;
        this.achievementCount = achievementCount;
        this.achievementType = achievementType;
    }


    public static MemberAchievementResponse of(MemberAchievement memberAchievement) {
        return
                MemberAchievementResponse.builder()
                        .genreName(memberAchievement.getGenre().getGenreName())
                        .achievementName(memberAchievement.getAchievement().getAchievementName())
                        .achievementRank(memberAchievement.getAchievementRank())
                        .achievementType(memberAchievement.getAchievementType())
                        .achievementCount(memberAchievement.getAchievementCount())
                        .achievementType(memberAchievement.getAchievementType())
                        .build();
    }

    public void updateAchievementPercent(double achievementPercent) {
        this.achievementPercent = achievementPercent;
    }
}
