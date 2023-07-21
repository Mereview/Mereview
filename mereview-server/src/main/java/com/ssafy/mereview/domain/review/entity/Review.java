package com.ssafy.mereview.domain.review.entity;

import com.ssafy.mereview.common.domain.BaseEntity;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.movie.entity.Movie;
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
public class Review extends BaseEntity {
    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 200)
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column(nullable = false)
    private int hit;
    @Column
    private String highlight;
    @Enumerated(EnumType.STRING)
    private EvaluationType evaluationType;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<Comment> reviewComments = new ArrayList<>();
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<KeywordWeight> keywordWeights = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Builder
    public Review(Long id, String title, String content, int hit, String highlight, EvaluationType evaluationType, List<Comment> reviewComments, List<KeywordWeight> keywordWeights, Member member, Movie movie) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.hit = hit;
        this.highlight = highlight;
        this.evaluationType = evaluationType;
        this.reviewComments = reviewComments;
        this.keywordWeights = keywordWeights;
        this.member = member;
        this.movie = movie;
    }
}
