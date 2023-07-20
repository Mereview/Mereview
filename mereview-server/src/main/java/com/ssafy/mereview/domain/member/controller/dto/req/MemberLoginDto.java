package com.ssafy.mereview.domain.member.controller.dto.req;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberLoginDto {

    private String email;

    private String password;

}
