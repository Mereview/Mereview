package com.ssafy.mereview.api.service.review.dto.request;

import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.review.entity.Review;
import com.ssafy.mereview.domain.review.entity.ReviewEvaluation;
import com.ssafy.mereview.domain.review.entity.ReviewEvaluationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewEvaluationCreateServiceRequest {

    private Long reviewId;
    private Long memberId;
    private ReviewEvaluationType type;

    @Builder
    public ReviewEvaluationCreateServiceRequest(Long reviewId, Long memberId, ReviewEvaluationType type) {
        this.reviewId = reviewId;
        this.memberId = memberId;
        this.type = type;
    }

    public ReviewEvaluation toEntity() {
        return ReviewEvaluation.builder()
                .review(Review.builder().id(reviewId).build())
                .member(Member.builder().id(memberId).build())
                .type(type)
                .build();
    }
}
