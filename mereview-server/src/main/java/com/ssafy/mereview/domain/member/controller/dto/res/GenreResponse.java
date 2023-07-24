package com.ssafy.mereview.domain.member.controller.dto.res;

import com.ssafy.mereview.domain.member.entity.Interest;
import com.ssafy.mereview.domain.member.entity.MemberAchievement;
import com.ssafy.mereview.domain.member.entity.UserTier;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class GenreResponse {

    private Long genreId;

    private String genreName;

    private boolean isUsing;

    @Builder
    public GenreResponse(Long genreId, String genreName, List<UserTier> userTiers, List<Interest> interests, List<MemberAchievement> memberAchievements, boolean isUsing) {
        this.genreId = genreId;
        this.genreName = genreName;
    }
}
