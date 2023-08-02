package com.ssafy.mereview.api.service.review.dto.response;

import com.ssafy.mereview.domain.review.entity.ReviewEvaluationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewEvaluationResponse {

    private Long reviewEvaluationId;
    private ReviewEvaluationType reviewEvaluationType;

    @Builder
    public ReviewEvaluationResponse(Long reviewEvaluationId, ReviewEvaluationType reviewEvaluationType) {
        this.reviewEvaluationId = reviewEvaluationId;
        this.reviewEvaluationType = reviewEvaluationType;
    }
}
