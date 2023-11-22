package com.ssafy.mereview.api.controller.review;

import com.ssafy.mereview.api.controller.review.dto.request.ReviewEvaluationRequest;
import com.ssafy.mereview.api.service.review.ReviewEvaluationService;
import com.ssafy.mereview.api.service.review.dto.response.ReviewEvaluationResponse;
import com.ssafy.mereview.common.response.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/reviews/evaluations")
@RestController
@Api(tags = {"평가 관련 API"})
public class EvaluationController {
    private final ReviewEvaluationService reviewEvaluationService;

    @PostMapping
    @ApiOperation(value = "리뷰 평가 생성")
    public ApiResponse<ReviewEvaluationResponse> updateReviewEvaluation(@Valid @RequestBody ReviewEvaluationRequest request) {
        ReviewEvaluationResponse response = reviewEvaluationService.update(request.toServiceRequest());
        return ApiResponse.ok(response);
    }

}
