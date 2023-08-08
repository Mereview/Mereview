package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.member.dto.response.MemberTierResponse;
import com.ssafy.mereview.api.service.member.dto.response.ProfileImageResponse;
import com.ssafy.mereview.api.service.movie.dto.response.GenreResponse;
import com.ssafy.mereview.api.service.review.dto.response.*;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.entity.MemberTier;
import com.ssafy.mereview.domain.member.entity.ProfileImage;
import com.ssafy.mereview.domain.movie.entity.Movie;
import com.ssafy.mereview.domain.review.entity.*;
import com.ssafy.mereview.domain.review.repository.dto.SearchCondition;
import com.ssafy.mereview.domain.review.repository.query.ReviewEvaluationQueryRepository;
import com.ssafy.mereview.domain.review.repository.query.ReviewQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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
    private final ReviewEvaluationQueryRepository reviewEvaluationQueryRepository;

    public List<ReviewResponse> searchByCondition(SearchCondition condition, Pageable pageable) {
        List<Review> reviews = reviewQueryRepository.searchByCondition(condition, pageable);
        log.debug("reviews: {}", reviews);

        List<ReviewResponse> responses = createReviewResponses(reviews);
        sortByReviewEvaluationTypeCounts(responses, condition.getOrderBy(), condition.getOrderDir());
        log.debug("responses: {}", responses);

        return responses;

    }

    public int calculatePageCount(SearchCondition condition) {
        return ((reviewQueryRepository.getTotalPages(condition) - 1) / PAGE_SIZE) + 1;
    }

    public ReviewDetailResponse searchById(Long reviewId, Long loginMemberId) {
        Review review = reviewQueryRepository.searchById(reviewId);
        if (review == null) {
            throw new NoSuchElementException("존재하지 않는 리뷰입니다.");
        }
        increaseHits(loginMemberId, review);
        return createReviewDetailResponse(review);
    }

    /**
     * private methods
     */

    private List<ReviewResponse> createReviewResponses(List<Review> reviews) {
        return reviews.stream()
                .map(review -> {
                            Movie movie = review.getMovie();
                            Member writeMember = review.getMember();
                            return ReviewResponse.builder()
                                    .reviewId(review.getId())
                                    .reviewTitle(review.getTitle())
                                    .hits(review.getHits())
                                    .highlight(review.getHighlight())
                                    .movieRecommendType(review.getType())
                                    .commentCount(review.getComments().size())
                                    .positiveCount(getPositiveCount(review.getId()))
                                    .funCount(getTypeCountByReviewAndType(FUN, review.getId()))
                                    .usefulCount(getTypeCountByReviewAndType(USEFUL, review.getId()))
                                    .badCount(getTypeCountByReviewAndType(BAD, review.getId()))
                                    .backgroundImageResponse(createBackgroundImageResponse(review.getBackgroundImage()))
                                    .createdTime(review.getCreatedTime())
                                    .memberId(writeMember.getId())
                                    .nickname(writeMember.getNickname())
                                    .profileImage(getProfileImageResponse(writeMember.getProfileImage()))
                                    .movieId(movie.getId())
                                    .movieTitle(movie.getTitle())
                                    .movieReleaseDate(movie.getReleaseDate())
                                    .genreResponse(GenreResponse.of(review.getGenre()))
                                    .build();
                        }
                ).collect(Collectors.toList());
    }

    private ReviewDetailResponse createReviewDetailResponse(Review review) {
        Member writeMember = review.getMember();
        Movie movie = review.getMovie();
        return ReviewDetailResponse.builder()
                .reviewId(review.getId())
                .reviewTitle(review.getTitle())
                .reviewContent(review.getContent())
                .hits(review.getHits())
                .backgroundImage(createBackgroundImageResponse(review.getBackgroundImage()))
                .reviewHighlight(review.getHighlight())
                .reviewCreatedTime(review.getCreatedTime())
                .keywords(getKeywordResponses(review.getKeywords()))
                .evaluated(isEvaluated(review.getId(), writeMember.getId()))
                .positiveCount(getPositiveCount(review.getId()))
                .funCount(getTypeCountByReviewAndType(FUN, review.getId()))
                .usefulCount(getTypeCountByReviewAndType(USEFUL, review.getId()))
                .badCount(getTypeCountByReviewAndType(BAD, review.getId()))
                .movieId(movie.getId())
                .movieTitle(movie.getTitle())
                .genre(GenreResponse.of(review.getGenre()))
                .movieReleaseDate(movie.getReleaseDate())
                .memberId(writeMember.getId())
                .nickname(writeMember.getNickname())
                .memberTiers(getMemberTierResponses(writeMember.getMemberTiers()))
                .profileImage(getProfileImageResponse(writeMember.getProfileImage()))
                .comments(getCommentResponses(review.getComments()))
                .build();
    }

    private void increaseHits(Long loginMemberId, Review review) {
        if (!review.getMember().getId().equals(loginMemberId)) {
            review.increaseHits();
        }
    }

    private boolean isEvaluated(Long reviewId, Long memberId) {
        Optional<ReviewEvaluation> reviewEvaluation = reviewEvaluationQueryRepository.searchByReviewAndMember(reviewId, memberId);
        return reviewEvaluation.isPresent();
    }

    private int getPositiveCount(Long reviewId) {
        return getTypeCountByReviewAndType(FUN, reviewId) + getTypeCountByReviewAndType(USEFUL, reviewId);
    }

    private int getTypeCountByReviewAndType(ReviewEvaluationType type, Long reviewId) {
        return reviewEvaluationQueryRepository.getCountByReviewIdAndType(reviewId, type);
    }

    private BackgroundImageResponse createBackgroundImageResponse(BackgroundImage backgroundImage) {
        if (backgroundImage == null) {
            return null;
        }
        return BackgroundImageResponse.of(backgroundImage);
    }

    private void sortByReviewEvaluationTypeCounts(List<ReviewResponse> responses, String orderBy, String orderDir) {
        switch (orderBy) {
            case "FUN":
                if (orderDir.equals("ASC")) {
                    responses.sort(comparingInt(ReviewResponse::getFunCount));
                } else {
                    responses.sort(comparingInt(ReviewResponse::getFunCount).reversed());
                }
                break;
            case "USEFUL":
                if (orderDir.equals("ASC")) {
                    responses.sort(comparingInt(ReviewResponse::getUsefulCount));
                } else {
                    responses.sort(comparingInt(ReviewResponse::getUsefulCount).reversed());
                }
                break;
            case "POSITIVE":
                if (orderDir.equals("ASC")) {
                    responses.sort(comparingInt(ReviewResponse::getPositiveCount));
                } else {
                    responses.sort(comparingInt(ReviewResponse::getPositiveCount).reversed());
                }
                break;
        }
    }

    private List<KeywordResponse> getKeywordResponses(List<Keyword> keywords) {
        return keywords.stream().map(KeywordResponse::of).collect(Collectors.toList());
    }

    private List<MemberTierResponse> getMemberTierResponses(List<MemberTier> memberTiers) {
        if (memberTiers == null) {
            return new ArrayList<>();
        }
        return memberTiers.stream().map(MemberTierResponse::of).collect(Collectors.toList());
    }

    private ProfileImageResponse getProfileImageResponse(ProfileImage profileImage) {
        if (profileImage == null) {
            return null;
        }
        return ProfileImageResponse.of(profileImage);
    }

    private List<CommentResponse> getCommentResponses(List<Comment> comments) {
        return comments.stream().map(CommentResponse::of).collect(Collectors.toList());
    }
}
