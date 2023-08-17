package com.ssafy.mereview.domain.review.entity;

import com.ssafy.mereview.api.service.review.dto.request.KeywordUpdateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.request.ReviewUpdateServiceRequest;
import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.BaseEntity;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.movie.entity.Genre;
import com.ssafy.mereview.domain.movie.entity.Movie;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;


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

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    private int hits;

    @Column(nullable = false)
    private String highlight;

    @Enumerated(EnumType.STRING)
    private MovieRecommendType type;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Keyword> keywords = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewEvaluation> evaluations = new ArrayList<>();

    @OneToOne(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private BackgroundImage backgroundImage;

    @Builder
    public Review(Long id, String title, String content, int hits, String highlight, MovieRecommendType type, Member member, Movie movie, Genre genre) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.hits = hits;
        this.highlight = highlight;
        this.type = type;
        this.member = member;
        this.movie = movie;
        this.genre = genre;
    }

    // Business Logic

    public void update(ReviewUpdateServiceRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.highlight = request.getHighlight();
        this.type = request.getType();
        updateKeywords(request.getKeywordServiceRequests(), request.getReviewId());
        updateBackGround(request);
    }

    private void updateBackGround(ReviewUpdateServiceRequest request) {
        if (request.getUpdateBackGround()) {
            this.backgroundImage = createUpdateBackgroundImage(request.getUploadFile());
        }
    }

    private BackgroundImage createUpdateBackgroundImage(UploadFile uploadFile) {
        return BackgroundImage.builder()
                .review(Review.builder().id(id).build())
                .uploadFile(uploadFile)
                .build();
    }

    private void updateKeywords(List<KeywordUpdateServiceRequest> keywordServiceRequests, Long reviewId) {
        this.keywords.clear();
        this.keywords = keywordServiceRequests.stream()
                .map(keyword -> keyword.toEntity(reviewId))
                .collect(Collectors.toList());
    }

    public void increaseHits() {
        this.hits++;
    }
}
