package com.ssafy.mereview.domain.review.entity;

import com.ssafy.mereview.api.service.review.dto.request.ReviewUpdateServiceRequest;
import com.ssafy.mereview.domain.BaseEntity;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.movie.entity.Genre;
import com.ssafy.mereview.domain.movie.entity.Movie;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;


@ToString
@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Review extends BaseEntity {
    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private int hits;

    @Column(nullable = false)
    private String highlight;

    @Enumerated(EnumType.STRING)
    private EvaluationType type;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToMany(mappedBy = "review")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "review")
    private List<Keyword> keywords = new ArrayList<>();

    @OneToMany(mappedBy = "review")
    private List<ReviewLike> likes = new ArrayList<>();

    @OneToOne(mappedBy = "review")
    private BackgroundImage backgroundImage;

    @Builder
    private Review(Long id, String title, String content, int hits, String highlight, EvaluationType type, Member member, Movie movie, Genre genre, List<Comment> comments, List<Keyword> keywords, List<ReviewLike> likes, BackgroundImage backgroundImage) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.hits = hits;
        this.highlight = highlight;
        this.type = type;
        this.member = member;
        this.movie = movie;
        this.genre = genre;
        this.comments = comments;
        this.keywords = keywords;
        this.likes = likes;
        this.backgroundImage = backgroundImage;
    }

    public void update(ReviewUpdateServiceRequest request, List<Keyword> keywords, BackgroundImage backgroundImage) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.highlight = request.getHighlight();
        this.type = request.getType();
        this.keywords = keywords;
        this.backgroundImage = backgroundImage;
    }
}
