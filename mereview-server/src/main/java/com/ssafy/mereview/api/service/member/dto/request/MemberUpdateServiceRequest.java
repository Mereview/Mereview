package com.ssafy.mereview.api.service.member.dto.request;

import com.ssafy.mereview.api.controller.member.dto.request.InterestRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MemberUpdateServiceRequest {

    private String nickname;

    private String gender;

    private String birthDate;

    private List<InterestRequest> interestRequests = new ArrayList<>();

    @Builder
    public MemberUpdateServiceRequest(String nickname, String gender, String birthDate, List<InterestRequest> interestRequests) {
        this.nickname = nickname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.interestRequests = interestRequests;
    }
}
