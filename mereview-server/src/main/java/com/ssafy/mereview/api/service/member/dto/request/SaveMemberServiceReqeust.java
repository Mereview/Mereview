package com.ssafy.mereview.api.service.member.dto.request;

import com.ssafy.mereview.api.controller.member.dto.request.InterestRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SaveMemberServiceReqeust {

    private String email;

    private String password;

    private List<InterestRequest> interestRequests = new ArrayList<>();


    @Builder

    public SaveMemberServiceReqeust(String email, String password, List<InterestRequest> interestRequests) {
        this.email = email;
        this.password = password;
        this.interestRequests = interestRequests;
    }
}
