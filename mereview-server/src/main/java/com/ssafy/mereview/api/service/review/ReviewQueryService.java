package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.member.dto.response.MemberTierResponse;
import com.ssafy.mereview.api.service.member.dto.response.ProfileImageResponse;
import com.ssafy.mereview.api.service.review.dto.response.*;
import com.ssafy.mereview.domain.member.entity.MemberTier;
import com.ssafy.mereview.domain.member.entity.ProfileImage;
import com.ssafy.mereview.domain.review.entity.Comment;
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

import java.util.ArrayList;
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
        sortByReviewEvaluationTypeAmounts(responses, condition.getOrderBy());
        log.debug("responses: {}", responses);

        return responses;

    }

    public int calculatePageCount(SearchCondition condition) {
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

    /**
     *  private methods
     */

    private List<ReviewResponse> createReviewResponses(List<Review> reviews) {
        return reviews.stream()
                .map(review -> ReviewResponse.builder()
                        .reviewId(review.getId())
                        .reviewTitle(review.getTitle())
                        .hits(review.getHits())
                        .highlight(review.getHighlight())
                        .evaluationType(review.getType())
                        .commentCount(review.getComments().size())
                        .funCount(getFunCount(review.getEvaluations()))
                        .usefulCount(getUsefulCount(review.getEvaluations()))
                        .badCount(getBadCount(review.getEvaluations()))
                        .backgroundImageResponse(getBackgroundImageResponse(review))
                        .createdTime(review.getCreatedTime())
                        .memberId(review.getMember().getId())
                        .nickname(review.getMember().getNickname())
                        .movieId(review.getMovie().getId())
                        .movieTitle(review.getMovie().getTitle())
                        .movieReleaseDate(review.getMovie().getReleaseDate())
                        .genreResponse(review.getGenre().of())
                        .build()
                ).collect(Collectors.toList());
    }

    private ReviewDetailResponse createReviewDetailResponse(Review review) {
        return ReviewDetailResponse.builder()
                .reviewId(review.getId())
                .reviewTitle(review.getTitle())
                .reviewContent(review.getContent())
                .hits(review.getHits())
                .backgroundImage(getBackgroundImageResponse(review))
                .reviewHighlight(review.getHighlight())
                .reviewCreatedTime(review.getCreatedTime())
                .keywords(getKeywordResponses(review.getKeywords()))
                .evaluated(false)   // TODO: 2023-08-03 평가여부 확인 메소드 구현 필요
                .funCount(getFunCount(review.getEvaluations()))
                .usefulCount(getUsefulCount(review.getEvaluations()))
                .badCount(getBadCount(review.getEvaluations()))
                .movieId(review.getMovie().getId())
                .movieTitle(review.getMovie().getTitle())
                .genre(review.getGenre().of())
                .movieReleaseDate(review.getMovie().getReleaseDate())
                .memberId(review.getMember().getId())
                .nickname(review.getMember().getNickname())
                .memberTiers(getMemberTierResponses(review.getMember().getMemberTiers()))
                .profileImage(getProfileImageResponse(review.getMember().getProfileImage()))
                .comments(getCommentResponses(review.getComments()))
                .build();
    }

    private static BackgroundImageResponse getBackgroundImageResponse(Review review) {
        if (review.getBackgroundImage() == null) {
            return null;
        }
        return review.getBackgroundImage().of();
    }

    private int getFunCount(List<ReviewEvaluation> evaluations) {
        return (int) evaluations.stream().filter(evaluation -> evaluation.getType().equals(FUN)).count();
    }

    private int getUsefulCount(List<ReviewEvaluation> evaluations) {
        return (int) evaluations.stream().filter(evaluation -> evaluation.getType().equals(USEFUL)).count();
    }

    private int getBadCount(List<ReviewEvaluation> evaluations) {
        return (int) evaluations.stream().filter(evaluation -> evaluation.getType().equals(BAD)).count();
    }

    private static void sortByReviewEvaluationTypeAmounts(List<ReviewResponse> responses, String orderBy) {
        if (orderBy.equals("FUN")) {
            responses.sort(comparingInt(ReviewResponse::getFunCount).reversed());
        } else if (orderBy.equals("USEFUL")) {
            responses.sort(comparingInt(ReviewResponse::getUsefulCount).reversed());
        }
    }

    private static List<KeywordResponse> getKeywordResponses(List<Keyword> keywords) {
        return keywords.stream().map(Keyword::of).collect(Collectors.toList());
    }

    private static List<MemberTierResponse> getMemberTierResponses(List<MemberTier> memberTiers) {
        if (memberTiers == null) {
            return new ArrayList<>();
        }
        return memberTiers.stream().map(MemberTier::of).collect(Collectors.toList());
    }

    private static ProfileImageResponse getProfileImageResponse(ProfileImage profileImage) {
        if (profileImage == null) {
            return null;
        }
        return profileImage.of();
    }

    private static List<CommentResponse> getCommentResponses(List<Comment> comments) {
        return comments.stream().map(Comment::of).collect(Collectors.toList());
    }
}
