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

    private String nickname;

    private String gender;

    private List<InterestRequest> interestRequests = new ArrayList<>();

    private String birthDate;

    List<InterestRequest> interests = new ArrayList<>();

    private UploadFile profileImage;
    private UploadFile uploadFile;

    private String verificationCode;


    @Builder
    public MemberCreateServiceRequest(String email, String password, String nickname, String gender, List<InterestRequest> interestRequests, String birthDate, List<InterestRequest> interests, UploadFile profileImage, UploadFile uploadFile, String verificationCode) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.interestRequests = interestRequests;
        this.birthDate = birthDate;
        this.interests = interests;
        this.profileImage = profileImage;
        this.uploadFile = uploadFile;
        this.verificationCode = verificationCode;
    }

    public Member toEntity(String password){
        return Member.builder()
                .email(email)
                .password(password)
                .gender(gender)
                .birthDate(birthDate)
                .nickname(nickname)
                .build();
    }
}
