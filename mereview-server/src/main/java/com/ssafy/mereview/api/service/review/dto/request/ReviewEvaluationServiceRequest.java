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
public class ReviewEvaluationServiceRequest {

    private Long reviewId;
    private Long memberId;
    private Long genreId;
    private ReviewEvaluationType type;

    @Builder
    public ReviewEvaluationServiceRequest(Long reviewId, Long memberId, Long genreId, ReviewEvaluationType type) {
        this.reviewId = reviewId;
        this.memberId = memberId;
        this.genreId = genreId;
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
