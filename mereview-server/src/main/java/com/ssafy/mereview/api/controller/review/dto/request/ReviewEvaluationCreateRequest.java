package com.ssafy.mereview.api.controller.review.dto.request;

import com.ssafy.mereview.api.service.review.dto.request.ReviewEvaluationCreateServiceRequest;
import com.ssafy.mereview.domain.review.entity.ReviewEvaluationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewEvaluationCreateRequest {

    private Long reviewId;
    private Long memberId;
    private ReviewEvaluationType type;

    @Builder
    public ReviewEvaluationCreateRequest(Long reviewId, Long memberId, ReviewEvaluationType type) {
        this.reviewId = reviewId;
        this.memberId = memberId;
        this.type = type;
    }

    public ReviewEvaluationCreateServiceRequest toServiceRequest() {
        return ReviewEvaluationCreateServiceRequest.builder()
                .reviewId(reviewId)
                .memberId(memberId)
                .type(type)
                .build();
    }
}
