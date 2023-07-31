package com.ssafy.mereview.domain.movie.entity;


import com.ssafy.mereview.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class MovieGenre extends BaseEntity {

    @Id
    @Column(name = "movie_genre_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "genre_id")
//    private Genre genre;

    @Builder
    public MovieGenre(Long id, Movie movie, Genre genre) {
        this.id = id;
        this.movie = movie;
        this.genre = genre;
    }
}
