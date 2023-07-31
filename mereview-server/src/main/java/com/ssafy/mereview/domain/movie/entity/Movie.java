package com.ssafy.mereview.domain.movie.entity;

import com.ssafy.mereview.domain.BaseEntity;
import com.ssafy.mereview.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Movie extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "movie_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private int movieContentId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String overview;

    private Double voteAverage;

    private String posterImg;

    private String releaseDate;

    // private String director;
    // private List<String> actor;

    @OneToMany(mappedBy = "movie")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<MovieGenre> movieGenres = new ArrayList<>();

    @Builder
    public Movie(Long id, int movieContentId, String title, String overview, Double voteAverage, String posterImg, String releaseDate, List<Review> reviews, List<MovieGenre> movieGenres) {
        this.id = id;
        this.movieContentId = movieContentId;
        this.title = title;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.posterImg = posterImg;
        this.releaseDate = releaseDate;
        this.reviews = reviews;
        this.movieGenres = movieGenres;
    }
}
