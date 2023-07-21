package com.ssafy.mereview.domain.member.service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaveMemberDto {

    private String email;

    private String password;

    @Builder
    public SaveMemberDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
