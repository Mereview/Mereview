package com.ssafy.mereview.api.service.member.dto.request;

import com.ssafy.mereview.api.controller.member.dto.request.InterestRequest;
import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class MemberCreateServiceRequest {

    private String email;
    private String password;
    private String nickname;
    private String gender;
    private String birthDate;
    List<InterestServiceRequest> interests = new ArrayList<>();
    private String verificationCode;

    @Builder
    public MemberCreateServiceRequest(String email, String password, String nickname, String gender, String birthDate, List<InterestServiceRequest> interests, String verificationCode) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.interests = interests;
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
