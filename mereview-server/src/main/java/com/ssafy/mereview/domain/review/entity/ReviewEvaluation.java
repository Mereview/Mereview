package com.ssafy.mereview.domain.review.entity;

import com.ssafy.mereview.common.domain.BaseEntity;
import com.ssafy.mereview.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewEvaluation extends BaseEntity {
    @Id
    @Column(name = "review_evaluation_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private EvaluationType evaluationType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public ReviewEvaluation(Long id, EvaluationType evaluationType, Review review, Member member) {
        this.id = id;
        this.evaluationType = evaluationType;
        this.review = review;
        this.member = member;
    }
}
