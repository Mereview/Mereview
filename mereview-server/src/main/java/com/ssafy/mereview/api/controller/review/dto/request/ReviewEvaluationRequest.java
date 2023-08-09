package com.ssafy.mereview.api.controller.review.dto.request;

import com.ssafy.mereview.api.service.review.dto.request.ReviewEvaluationServiceRequest;
import com.ssafy.mereview.domain.review.entity.ReviewEvaluationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ReviewEvaluationRequest {

    @NotNull
    private Long reviewId;
    @NotNull
    private Long memberId;
    @NotNull
    private Long genreId;
    @NotBlank
    private ReviewEvaluationType type;

    @Builder
    public ReviewEvaluationRequest(Long reviewId, Long memberId, Long genreId, ReviewEvaluationType type) {
        this.reviewId = reviewId;
        this.memberId = memberId;
        this.genreId = genreId;
        this.type = type;
    }

    public ReviewEvaluationServiceRequest toServiceRequest() {
        return ReviewEvaluationServiceRequest.builder()
                .reviewId(reviewId)
                .memberId(memberId)
                .genreId(genreId)
                .type(type)
                .build();
    }
}
