package com.ssafy.mereview.domain.member.service.dto;

import com.ssafy.mereview.domain.member.entity.Interest;
import com.ssafy.mereview.domain.movie.entity.Genre;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SaveMemberDto {

    private String email;

    private String password;

    private List<Genre> genres = new ArrayList<>();


    @Builder
    public SaveMemberDto(String email, String password, List<Genre> genres) {
        this.email = email;
        this.password = password;
        this.genres = genres;
    }
}
