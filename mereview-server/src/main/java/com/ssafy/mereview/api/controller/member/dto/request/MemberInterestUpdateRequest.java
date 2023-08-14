package com.ssafy.mereview.api.controller.member.dto.request;

import com.ssafy.mereview.api.service.member.dto.request.InterestServiceRequest;
import com.ssafy.mereview.api.service.member.dto.request.MemberUpdateServiceRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class MemberInterestUpdateRequest {

    private String nickname;

    List<InterestRequest> interests = new ArrayList<>();

    @Builder
    public MemberInterestUpdateRequest(String nickname, List<InterestRequest> interests) {
        this.nickname = nickname;
        this.interests = interests;
    }



    public MemberUpdateServiceRequest toServiceRequest() {
        return MemberUpdateServiceRequest.builder()
                .interests(createInterestServiceRequests())
                .build();
    }

    private List<InterestServiceRequest> createInterestServiceRequests() {
        return interests.stream().map(InterestRequest::toServiceRequest).collect(Collectors.toList());
    }
}
