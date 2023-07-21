package com.ssafy.mereview.domain.movie.entity;

import com.ssafy.mereview.common.domain.BaseEntity;
import com.ssafy.mereview.domain.review.entity.Review;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;

    private String title;

    private String overview;

    private Double popularity;

    private String posterImg;

    private String releaseDate;

    @OneToMany(mappedBy = "movie")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<MovieGenre> movieGenres = new ArrayList<>();

    @Builder
    public Movie(Long id, String title, String overview, Double popularity, String posterImg, String releaseDate, List<Review> reviews, List<MovieGenre> movieGenres) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.popularity = popularity;
        this.posterImg = posterImg;
        this.releaseDate = releaseDate;
        this.reviews = reviews;
        this.movieGenres = movieGenres;
    }
}
