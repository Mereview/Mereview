package com.ssafy.mereview.domain.member.controller.dto.res;

import com.ssafy.mereview.domain.member.entity.Interest;
import com.ssafy.mereview.domain.member.entity.Role;
import com.ssafy.mereview.domain.member.entity.UserTier;
import com.ssafy.mereview.domain.review.entity.Review;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class MemberResponse {


    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String gender;
    private String birthDate;
    private Role role;
    private List<GenreResponse> genres;

    @Builder

    public MemberResponse(Long id, String email, String password, String nickname, String gender, String birthDate, Role role, List<GenreResponse> genres) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.role = role;
        this.genres = genres;
    }
}
