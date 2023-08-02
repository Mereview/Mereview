package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.review.dto.response.*;
import com.ssafy.mereview.domain.member.entity.MemberTier;
import com.ssafy.mereview.domain.review.entity.Keyword;
import com.ssafy.mereview.domain.review.entity.Review;
import com.ssafy.mereview.domain.review.entity.ReviewEvaluation;
import com.ssafy.mereview.domain.review.repository.ReviewQueryRepository;
import com.ssafy.mereview.domain.review.repository.dto.SearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.ssafy.mereview.common.util.SizeConstants.PAGE_SIZE;
import static com.ssafy.mereview.domain.review.entity.ReviewEvaluationType.*;
import static java.util.Comparator.comparingInt;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReviewQueryService {
    private final ReviewQueryRepository reviewQueryRepository;

    public List<ReviewResponse> searchByCondition(SearchCondition condition, Pageable pageable) {
        List<Review> reviews = reviewQueryRepository.searchByCondition(condition, pageable);
        log.debug("reviews: {}", reviews);
        List<ReviewResponse> responses = createReviewResponses(reviews);

        if (condition.getOrderBy().equals("FUN")) {
            responses.sort(comparingInt(ReviewResponse::getFunCount).reversed());
        } else if (condition.getOrderBy().equals("USEFUL")) {
            responses.sort(comparingInt(ReviewResponse::getUsefulCount).reversed());
        }
        return responses;

    }

    public int calculatePages(SearchCondition condition) {
        return ((reviewQueryRepository.getTotalPages(condition) - 1) / PAGE_SIZE) + 1;
    }

    public ReviewDetailResponse searchById(Long reviewId) {
        Review review = reviewQueryRepository.searchById(reviewId);
        if (review == null) {
            throw new NoSuchElementException("존재하지 않는 리뷰입니다.");
        }
        review.increaseHits();
        return createReviewDetailResponse(review);
    }

    private List<ReviewResponse> createReviewResponses(List<Review> reviews) {
        return reviews.stream()
                .map(review -> ReviewResponse.builder()
                        .reviewId(review.getId())
                        .memberId(review.getMember().getId())
                        .nickname(review.getMember().getNickname())
                        .movieId(review.getMovie().getId())
                        .movieTitle(review.getMovie().getTitle())
                        .genreResponse(review.getGenre().of())
                        .movieReleaseDate(review.getMovie().getReleaseDate())
                        .reviewTitle(review.getTitle())
                        .hits(review.getHits())
                        .highlight(review.getHighlight())
                        .evaluationType(review.getType())
                        .commentCount(getCommentCount(review))
                        .funCount(getFunCount(review.getEvaluations()))
                        .usefulCount(getUsefulCount(review.getEvaluations()))
                        .badCount(getBadCount(review.getEvaluations()))
                        .backgroundImageResponse(getBackgroundImageResponse(review))
                        .createdTime(review.getCreatedTime())
                        .build()
                ).collect(Collectors.toList());
    }

    private static int getCommentCount(Review review) {
        if (review.getComments() != null) {
            return review.getComments().size();
        }
        return 0;
    }

    private static BackgroundImageResponse getBackgroundImageResponse(Review review) {
        if (review.getBackgroundImage() != null) {
            return review.getBackgroundImage().of();
        }
        return BackgroundImageResponse.builder().build();
    }

    private int getFunCount(List<ReviewEvaluation> likes) {
        if (likes != null) {
            return (int) likes.stream().filter(like -> like.getType().equals(FUN)).count();
        }
        return 0;
    }

    private int getUsefulCount(List<ReviewEvaluation> likes) {
        if (likes != null) {
            return (int) likes.stream().filter(like -> like.getType().equals(USEFUL)).count();
        }
        return 0;
    }

    private int getBadCount(List<ReviewEvaluation> likes) {
        if (likes != null) {
            return (int) likes.stream().filter(like -> like.getType().equals(BAD)).count();
        }
        return 0;
    }

    private static ReviewDetailResponse createReviewDetailResponse(Review review) {
        return ReviewDetailResponse.builder()
                .reviewId(review.getId())
                .reviewTitle(review.getTitle())
                .reviewContent(review.getContent())
                .hits(review.getHits())
                .backgroundImage(getBackgroundImageResponse(review))
                .reviewHighlight(review.getHighlight())
                .reviewCreatedTime(review.getCreatedTime())
                .keywords(createKeywords(review))
                .reviewEvaluations(createReviewLikes(review))
                .movieId(review.getMovie().getId())
                .movieTitle(review.getMovie().getTitle())
                .genreResponse(review.getGenre().of())
                .movieReleaseDate(review.getMovie().getReleaseDate())
                .memberId(review.getMember().getId())
                .nickname(review.getMember().getNickname())
                .memberTiers(review.getMember().getMemberTiers().stream().map(MemberTier::of).collect(Collectors.toList()))
                .build();
    }

    private static List<KeywordResponse> createKeywords(Review review) {
        return review.getKeywords().stream().map(Keyword::of).collect(Collectors.toList());
    }

    private static List<ReviewEvaluationResponse> createReviewLikes(Review review) {
        return review.getEvaluations().stream().map(ReviewEvaluation::of).collect(Collectors.toList());
    }
}
