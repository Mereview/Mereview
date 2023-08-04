package com.ssafy.mereview.api.service.member.dto.response;

import com.ssafy.mereview.domain.member.entity.MemberTier;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberTierResponse {

    private String funTier;
    private String usefulTier;
    private int funExperience;
    private int usefulExperience;
    private String genreName;

    @Builder
    public MemberTierResponse(String funTier, String usefulTier, int funExperience, int usefulExperience, String genreName) {
        this.funTier = funTier;
        this.usefulTier = usefulTier;
        this.funExperience = funExperience;
        this.usefulExperience = usefulExperience;
        this.genreName = genreName;
    }

    public static MemberTierResponse of(MemberTier memberTier){
        return MemberTierResponse.builder()
                .funTier(memberTier.getFunTier())
                .usefulTier(memberTier.getUsefulTier())
                .funExperience(memberTier.getFunExperience())
                .usefulExperience(memberTier.getUsefulExperience())
                .genreName(memberTier.getGenre().getGenreName())
                .build();
    }
}

