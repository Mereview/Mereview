package com.ssafy.mereview.api.controller.member.dto.request;

import com.ssafy.mereview.domain.member.entity.Interest;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.movie.entity.Genre;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InterestRequest {

    private Long genreId;

    private String genreName;

    private boolean isUsing;


    @Builder
    public InterestRequest(Long genreId, String genreName, boolean isUsing) {
        this.genreId = genreId;
        this.genreName = genreName;
        this.isUsing = isUsing;
    }
    public Interest toEntity(Long memberId){
        return Interest.builder()
                .member(Member.builder().id(memberId).build())
                .genre(Genre.builder().id(genreId).genreName(genreName).build())
                .build();

    }
}
