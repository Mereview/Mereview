package com.ssafy.mereview.api.service.member.dto.request;

import com.ssafy.mereview.api.controller.member.dto.request.InterestRequest;
import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.member.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MemberUpdateServiceRequest {

    Long id;

    private String nickname;

    private String gender;

    private String birthDate;

    private List<InterestRequest> interestRequests = new ArrayList<>();

    @Builder
    public MemberUpdateServiceRequest(Long id, String nickname, String gender, String birthDate, List<InterestRequest> interestRequests) {
        this.id = id;
        this.nickname = nickname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.interestRequests = interestRequests;
    }
}
