package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.review.dto.request.ReviewEvaluationCreateServiceRequest;
import com.ssafy.mereview.domain.review.entity.ReviewEvaluation;
import com.ssafy.mereview.domain.review.repository.ReviewEvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional
@Service
public class ReviewEvaluationService {

    private final ReviewEvaluationRepository reviewEvaluationRepository;

    public Long createReviewEvaluation(ReviewEvaluationCreateServiceRequest request) {
        return reviewEvaluationRepository.save(request.toEntity()).getId();
    }

    public Long delete(Long evaluationId) {
        ReviewEvaluation reviewEvaluation = reviewEvaluationRepository.findById(evaluationId)
                .orElseThrow(NoSuchElementException::new);
        reviewEvaluationRepository.delete(reviewEvaluation);
        return reviewEvaluation.getId();
    }
}
