package com.ssafy.mereview.domain.member.entity;


import com.ssafy.mereview.api.service.member.dto.response.InterestResponse;
import com.ssafy.mereview.domain.BaseEntity;
import com.ssafy.mereview.domain.movie.entity.Genre;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Interest extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Builder
    public Interest(Long id, Member member, Genre genre) {
        this.id = id;
        this.member = member;
        this.genre = genre;
    }

    public InterestResponse of(){
        return InterestResponse.builder()
                .genreId(genre.getId())
                .genreName(genre.getGenreName())
                .build();
    }
}
