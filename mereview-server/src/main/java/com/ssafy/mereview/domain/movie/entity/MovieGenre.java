package com.ssafy.mereview.domain.movie.entity;

import com.ssafy.mereview.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieGenre extends BaseEntity {

    @Id
    @Column(name = "movie_genre_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Builder
    public MovieGenre(Long id, Movie movie, Genre genre) {
        this.id = id;
        this.movie = movie;
        this.genre = genre;
    }
}
