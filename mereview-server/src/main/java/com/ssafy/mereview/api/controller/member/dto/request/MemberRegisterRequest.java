package com.ssafy.mereview.api.controller.member.dto.request;

import com.ssafy.mereview.api.service.member.dto.request.MemberCreateServiceRequest;
import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.member.entity.*;
import com.ssafy.mereview.domain.review.entity.Review;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class MemberRegisterRequest {

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "비밀번호는 영문 소문자, 숫자, 특수문자(@$!%*?&)를 포함해야 합니다.")
    private String password;

    private String nickname;

    private String gender;

    private String birthDate;

    private ProfileImage profileImage;

    List<InterestRequest> interests = new ArrayList<>();

    private UploadFile uploadFile;

    @Builder
    public MemberRegisterRequest(String email, String password, String nickname, String gender, String birthDate, ProfileImage profileImage, List<InterestRequest> interests, UploadFile uploadFile) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.profileImage = profileImage;
        this.interests = interests;
        this.uploadFile = uploadFile;
    }

    public MemberCreateServiceRequest toMemberCreateServiceRequest() {
        return MemberCreateServiceRequest.builder()
                .email(email)
                .password(password)
                .uploadFile(uploadFile)
                .interestRequests(createInterestRequests())
                .build();
    }

    private List<InterestRequest> createInterestRequests() {
        return this.interests.stream().map(interest -> InterestRequest.builder()
                .genreName(interest.getGenreName())
                .build()).collect(Collectors.toList());
    }
}
