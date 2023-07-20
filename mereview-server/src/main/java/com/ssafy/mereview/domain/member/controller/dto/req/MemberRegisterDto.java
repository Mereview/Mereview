package com.ssafy.mereview.domain.member.controller.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberRegisterDto {
    String email;
    String password;
}
