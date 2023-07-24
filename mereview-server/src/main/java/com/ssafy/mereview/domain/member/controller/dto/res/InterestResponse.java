package com.ssafy.mereview.domain.member.controller.dto.res;

import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.movie.entity.Genre;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
public class InterestResponse {
    private Long id;

    private Member member;

    private Genre genre;

    @Builder
    public InterestResponse(Long id, Member member, Genre genre) {
        this.id = id;
        this.member = member;
        this.genre = genre;
    }
}
