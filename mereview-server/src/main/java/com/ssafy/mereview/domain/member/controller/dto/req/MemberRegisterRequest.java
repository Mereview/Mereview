package com.ssafy.mereview.domain.member.controller.dto.req;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MemberRegisterRequest {
    String email;
    String password;
    List<Long> genres = new ArrayList<>();

    @Builder

    public MemberRegisterRequest(String email, String password, List<Long> genres) {
        this.email = email;
        this.password = password;
        this.genres = genres;
    }
}
