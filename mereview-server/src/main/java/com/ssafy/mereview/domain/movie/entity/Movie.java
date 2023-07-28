package com.ssafy.mereview.domain.movie.entity;

import com.ssafy.mereview.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Movie extends BaseEntity {
    @Id
    @Column(name = "movie_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
}
