package com.ssafy.mereview.domain.member.controller.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

@Data
@NoArgsConstructor
public class MemberRegisterDto {
    String email;
    String password;

    @Builder
    public MemberRegisterDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
