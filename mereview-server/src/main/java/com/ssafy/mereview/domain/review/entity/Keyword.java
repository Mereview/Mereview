package com.ssafy.mereview.domain.review.entity;

import com.ssafy.mereview.api.service.review.dto.response.KeywordResponse;
import com.ssafy.mereview.domain.BaseEntity;
import com.ssafy.mereview.domain.movie.entity.Movie;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Keyword extends BaseEntity {
    @Id
    @Column(name = "keyword_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private int weight;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Builder
    private Keyword(Long id, String name, int weight, Review review, Movie movie) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.review = review;
        this.movie = movie;
    }

    public KeywordResponse of() {
        return KeywordResponse.builder()
                .keywordId(id)
                .keywordName(name)
                .keywordWeight(weight)
                .build();
    }
}
