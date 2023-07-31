package com.ssafy.mereview.api.controller.member.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MemberRegisterRequest {
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "비밀번호는 영문 소문자, 숫자, 특수문자(@$!%*?&)를 포함해야 합니다.")
    String password;

    List<String> interests = new ArrayList<>();


    @Builder
    public MemberRegisterRequest(String email, String password, List<String> interests) {
        this.email = email;
        this.password = password;
        this.interests = interests;
    }
}
