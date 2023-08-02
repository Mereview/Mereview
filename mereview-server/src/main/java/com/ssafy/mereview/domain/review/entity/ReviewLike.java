package com.ssafy.mereview.domain.review.entity;

import com.ssafy.mereview.api.service.review.dto.response.ReviewLikeResponse;
import com.ssafy.mereview.domain.BaseEntity;
import com.ssafy.mereview.domain.member.entity.Member;
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
public class ReviewLike extends BaseEntity {
    @Id
    @Column(name = "review_like_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    ReviewLikeType type;

    @ManyToOne(fetch = LAZY)
    private Review review;

    @ManyToOne(fetch = LAZY)
    private Member member;

    @Builder
    private ReviewLike(Long id, ReviewLikeType type, Review review, Member member) {
        this.id = id;
        this.type = type;
        this.review = review;
        this.member = member;
    }

    public ReviewLikeResponse of() {
        return ReviewLikeResponse.builder()
                .reviewLikeId(id)
                .reviewLikeType(type)
                .build();
    }
}
