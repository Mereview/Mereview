package com.ssafy.mereview.api.service.review.dto.response;

import com.ssafy.mereview.domain.review.entity.ReviewEvaluationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewEvaluationResponse {

    private ReviewEvaluationType reviewEvaluationType;
    private boolean isDone;
    private int funCount;
    private int usefulCount;
    private int badCount;

    @Builder
    public ReviewEvaluationResponse(ReviewEvaluationType reviewEvaluationType, boolean isDone, int funCount, int usefulCount, int badCount) {
        this.reviewEvaluationType = reviewEvaluationType;
        this.isDone = isDone;
        this.funCount = funCount;
        this.usefulCount = usefulCount;
        this.badCount = badCount;
    }
}
