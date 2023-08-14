package com.ssafy.mereview.api.service.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AchievementCountUpdateServiceRequest{
    Long memberId;

    Long genreId;

    int achievementType;

    @Builder
    public AchievementCountUpdateServiceRequest(Long memberId, Long genreId, int achievementType) {
        this.memberId = memberId;
        this.genreId = genreId;
        this.achievementType = achievementType;
    }
}
