package com.ssafy.mereview.api.controller.member.dto.request;

import com.ssafy.mereview.api.service.member.dto.request.AchievementCountUpdateServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AchievementCountUpdateRequest {
    Long memberId;

    Long genreId;

    int achievementType;

    @Builder

    public AchievementCountUpdateServiceRequest toServiceRequest() {
        return AchievementCountUpdateServiceRequest.builder()
                .memberId(memberId)
                .genreId(genreId)
                .build();
    }
}
