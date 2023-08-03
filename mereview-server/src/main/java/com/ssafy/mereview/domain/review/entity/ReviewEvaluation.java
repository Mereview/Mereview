package com.ssafy.mereview.domain.review.entity;

import com.ssafy.mereview.api.service.review.dto.response.ReviewEvaluationResponse;
import com.ssafy.mereview.domain.BaseEntity;
import com.ssafy.mereview.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class ReviewEvaluation extends BaseEntity {
    @Id
    @Column(name = "review_evaluation_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    ReviewEvaluationType type;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private ReviewEvaluation(Long id, ReviewEvaluationType type, Review review, Member member) {
        this.id = id;
        this.type = type;
        this.review = review;
        this.member = member;
    }

    public ReviewEvaluationResponse of() {
        return ReviewEvaluationResponse.builder()
                .reviewEvaluationId(id)
                .reviewEvaluationType(type)
                .build();
    }
}
