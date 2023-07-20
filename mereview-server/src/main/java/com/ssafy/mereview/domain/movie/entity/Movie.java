package com.ssafy.mereview.domain.movie.entity;

import com.ssafy.mereview.common.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Movie extends BaseEntity {

    @Id
    @Column(name = "movie_id")
    Long id;

    @Column(name = "movie_title")
    String title;

    @Column(name = "movie_overview")
    String overview;

    @Column(name = "movie_populity")
    Double populity;

    @Column(name = "movie_poster_img")
    String posterImg;

    @Column(name = "movie_release_date")
    String releaseDate;


}
