package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.review.dto.request.ReviewEvaluationServiceRequest;
import com.ssafy.mereview.api.service.review.dto.response.ReviewEvaluationResponse;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.entity.MemberTier;
import com.ssafy.mereview.domain.member.repository.MemberRepository;
import com.ssafy.mereview.domain.member.repository.MemberTierQueryRepository;
import com.ssafy.mereview.domain.member.repository.MemberTierRepository;
import com.ssafy.mereview.domain.review.entity.ReviewEvaluation;
import com.ssafy.mereview.domain.review.entity.ReviewEvaluationType;
import com.ssafy.mereview.domain.review.repository.query.ReviewEvaluationQueryRepository;
import com.ssafy.mereview.domain.review.repository.command.ReviewEvaluationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

        MemberTier memberTier = memberTierQueryRepository.searchMemberTierByMemberId(request.getMemberId(), request.getGenreId());
        if (request.getType().equals(BAD)) {
            memberTier.decreaseExp();
        } else {
            memberTier.increaseExp(request.getType());
        }

        return createReviewEvaluationResponse(request, isDone, evaluationCountsMap);

    }

    /**
     * private methods
     */

    private boolean updateReviewEvaluation(ReviewEvaluationServiceRequest request, Optional<ReviewEvaluation> reviewEvaluation) {
        if (reviewEvaluation.isEmpty()) {
            evaluationRepository.save(request.toEntity());
            return true;
        }
        reviewEvaluation.ifPresent(evaluationRepository::delete);
        return false;
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
