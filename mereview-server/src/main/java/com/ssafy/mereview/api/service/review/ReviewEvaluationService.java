package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.review.dto.request.ReviewEvaluationServiceRequest;
import com.ssafy.mereview.api.service.review.dto.response.ReviewEvaluationResponse;
import com.ssafy.mereview.domain.member.entity.MemberTier;
import com.ssafy.mereview.domain.member.repository.MemberTierQueryRepository;
import com.ssafy.mereview.domain.review.entity.ReviewEvaluation;
import com.ssafy.mereview.domain.review.entity.ReviewEvaluationType;
import com.ssafy.mereview.domain.review.repository.query.ReviewEvaluationQueryRepository;
import com.ssafy.mereview.domain.review.repository.command.ReviewEvaluationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.ssafy.mereview.domain.review.entity.ReviewEvaluationType.*;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ReviewEvaluationService {

    private final ReviewEvaluationRepository evaluationRepository;
    private final ReviewEvaluationQueryRepository evaluationQueryRepository;
    private final MemberTierQueryRepository memberTierQueryRepository;

    public ReviewEvaluationResponse update(ReviewEvaluationServiceRequest request) {
        Optional<ReviewEvaluation> reviewEvaluation = evaluationQueryRepository.searchByReviewAndMember(request.getReviewId(), request.getMemberId());

        boolean isDone = updateReviewEvaluation(request, reviewEvaluation);

        Map<ReviewEvaluationType, Integer> evaluationCountsMap = evaluationQueryRepository.getCountByReviewIdGroupedByType(request.getReviewId());

        return createReviewEvaluationResponse(request, isDone, evaluationCountsMap);

    }

    /**
     * private methods
     */

    private boolean updateReviewEvaluation(ReviewEvaluationServiceRequest request, Optional<ReviewEvaluation> reviewEvaluation) {
        if (reviewEvaluation.isEmpty()) {
            createReviewEvaluation(request);
            return true;
        }
        ReviewEvaluation evaluation = reviewEvaluation.orElseThrow(NoSuchElementException::new);
        if (evaluation.getType().equals(request.getType())) {
            deleteReviewEvaluation(request, evaluation);
            return false;
        }
        throw new DuplicateKeyException("한번에 하나의 평가만 할 수 있습니다.");
    }

    private void createReviewEvaluation(ReviewEvaluationServiceRequest request) {
        evaluationRepository.save(request.toEntity());
        updateExperienceAfterEvaluation(request);
    }

    private void deleteReviewEvaluation(ReviewEvaluationServiceRequest request, ReviewEvaluation evaluation) {
        evaluationRepository.delete(evaluation);
        updateExperienceAfterCancelEvaluation(request);
    }

    private void updateExperienceAfterEvaluation(ReviewEvaluationServiceRequest request) {
        MemberTier memberTier = memberTierQueryRepository.searchMemberTierByMemberId(request.getMemberId(), request.getGenreId());
        log.debug("memberTier: {}", memberTier);
        if (request.getType().equals(BAD)) {
            memberTier.decreaseExp();
        } else {
            memberTier.increaseExp(request.getType());
        }
    }

    private void updateExperienceAfterCancelEvaluation(ReviewEvaluationServiceRequest request) {
        MemberTier memberTier = memberTierQueryRepository.searchMemberTierByMemberId(request.getMemberId(), request.getGenreId());
        log.debug("memberTier: {}", memberTier);
        if (request.getType().equals(BAD)) {
            memberTier.increaseExp();
        } else {
            memberTier.decreaseExp(request.getType());
        }
    }

    private ReviewEvaluationResponse createReviewEvaluationResponse(ReviewEvaluationServiceRequest request, boolean isDone, Map<ReviewEvaluationType, Integer> evaluationCountsMap) {
        return ReviewEvaluationResponse.builder()
                .reviewEvaluationType(request.getType())
                .isDone(isDone)
                .funCount(evaluationCountsMap.getOrDefault(FUN, 0))
                .usefulCount(evaluationCountsMap.getOrDefault(USEFUL, 0))
                .badCount(evaluationCountsMap.getOrDefault(BAD, 0))
                .build();
    }

}
