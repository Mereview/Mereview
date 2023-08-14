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


    private String gender;

    private String birthDate;

    private List<InterestServiceRequest> interests = new ArrayList<>();

    @Builder
    public MemberUpdateServiceRequest(String gender, String birthDate, List<InterestServiceRequest> interests) {
        this.gender = gender;
        this.birthDate = birthDate;
        this.interests = interests;
    }
}
