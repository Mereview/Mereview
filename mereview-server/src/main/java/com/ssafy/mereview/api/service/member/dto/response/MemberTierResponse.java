package com.ssafy.mereview.api.service.member.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
}

