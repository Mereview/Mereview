package com.ssafy.mereview.api.service.member.dto.request;

import com.ssafy.mereview.api.controller.member.dto.request.InterestRequest;
import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.member.entity.Interest;
import com.ssafy.mereview.domain.member.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MemberCreateServiceRequest {

    private String email;

    private String password;

    private List<InterestRequest> interestRequests = new ArrayList<>();

    private UploadFile uploadFile;


    @Builder
    public MemberCreateServiceRequest(Long id, String email, String password, List<InterestRequest> interestRequests, UploadFile uploadFile) {
        this.email = email;
        this.password = password;
        this.interestRequests = interestRequests;
        this.uploadFile = uploadFile;
    }

    public Member toEntity(){
        return Member.builder()
                .email(email)
                .password(password)
                .build();
    }
}
