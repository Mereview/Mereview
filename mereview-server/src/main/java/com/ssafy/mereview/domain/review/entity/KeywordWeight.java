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
public class KeywordWeight extends BaseEntity {
    @Id
    @Column(name = "keyword_weight_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public KeywordWeight(Long id, int weight, Keyword keyword, Review review, Member member) {
        this.id = id;
        this.weight = weight;
        this.keyword = keyword;
        this.review = review;
        this.member = member;
    }
}
