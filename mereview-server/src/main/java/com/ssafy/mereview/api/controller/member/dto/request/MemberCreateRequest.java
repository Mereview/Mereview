package com.ssafy.mereview.api.controller.member.dto.request;

import com.ssafy.mereview.api.service.member.dto.request.InterestServiceRequest;
import com.ssafy.mereview.api.service.member.dto.request.MemberCreateServiceRequest;
import com.ssafy.mereview.common.util.file.UploadFile;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class MemberCreateRequest {

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%#*?&])[A-Za-z\\d@$!%*#?&]+$",
            message = "비밀번호는 영문 소문자, 숫자, 특수문자(@$#!%*?&)를 포함해야 합니다.")
    private String password;

    private String nickname;

    private String gender;

    private String birthDate;

    List<InterestRequest> interests = new ArrayList<>();

    private UploadFile profileImage;

    private String verificationCode;

    @Builder
    public MemberCreateRequest(String email, String password, String nickname, String gender, String birthDate, List<InterestRequest> interests, UploadFile profileImage, String verificationCode) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.interests = interests;
        this.profileImage = profileImage;
        this.verificationCode = verificationCode;
    }

    public MemberCreateServiceRequest toServiceRequest() {
        return MemberCreateServiceRequest.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .verificationCode(verificationCode)
                .gender(gender)
                .birthDate(birthDate)
                .interests(createInterestRequests())
                .build();
    }

    private List<InterestServiceRequest> createInterestRequests() {
        return interests.stream().map(interest -> InterestServiceRequest.builder()
                .genreName(interest.getGenreName())
                .genreId(interest.getGenreId())
                .genreNumber(interest.getGenreNumber())
                .build()).collect(Collectors.toList());
    }
}
