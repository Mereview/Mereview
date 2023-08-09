package com.ssafy.mereview.api.service.member.dto.request;

import com.ssafy.mereview.domain.member.entity.Interest;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.movie.entity.Genre;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InterestServiceRequest {

    private Long genreId;
    private int genreNumber;
    private String genreName;
    private boolean isUsing;

    @Builder
    public InterestServiceRequest(Long genreId, int genreNumber, String genreName, boolean isUsing) {
        this.genreId = genreId;
        this.genreNumber = genreNumber;
        this.genreName = genreName;
        this.isUsing = isUsing;
    }

    public Interest toEntity(Long memberId, Long genreId){
        return Interest.builder()
                .member(Member.builder().id(memberId).build())
                .genre(Genre.builder().id(genreId).build())
                .build();

    }
}
