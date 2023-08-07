package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.member.dto.response.MemberTierResponse;
import com.ssafy.mereview.api.service.member.dto.response.ProfileImageResponse;
import com.ssafy.mereview.api.service.movie.dto.response.GenreResponse;
import com.ssafy.mereview.api.service.review.dto.response.*;
import com.ssafy.mereview.common.util.jwt.JwtUtils;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.entity.MemberTier;
import com.ssafy.mereview.domain.member.entity.ProfileImage;
import com.ssafy.mereview.domain.movie.entity.Movie;
import com.ssafy.mereview.domain.review.entity.*;
import com.ssafy.mereview.domain.review.repository.ReviewEvaluationQueryRepository;
import com.ssafy.mereview.domain.review.repository.ReviewQueryRepository;
import com.ssafy.mereview.domain.review.repository.dto.SearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
        sortByReviewEvaluationTypeCounts(responses, condition.getOrderBy());
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
                                    .evaluationType(review.getType())
                                    .commentCount(review.getComments().size())
                                    .funCount(getTypeCount(FUN, review.getId()))
                                    .usefulCount(getTypeCount(USEFUL, review.getId()))
                                    .badCount(getTypeCount(BAD, review.getId()))
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
                .funCount(getTypeCount(FUN, review.getId()))
                .usefulCount(getTypeCount(USEFUL, review.getId()))
                .badCount(getTypeCount(BAD, review.getId()))
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

    private int getTypeCount(ReviewEvaluationType type, Long reviewId) {
        return reviewEvaluationQueryRepository.getCountByReviewIdAndType(reviewId, type);
    }

    private BackgroundImageResponse createBackgroundImageResponse(BackgroundImage backgroundImage) {
        if (backgroundImage == null) {
            return null;
        }
        return BackgroundImageResponse.of(backgroundImage);
    }

    private void sortByReviewEvaluationTypeCounts(List<ReviewResponse> responses, String orderBy) {
        if (orderBy.equals("FUN")) {
            responses.sort(comparingInt(ReviewResponse::getFunCount).reversed());
        } else if (orderBy.equals("USEFUL")) {
            responses.sort(comparingInt(ReviewResponse::getUsefulCount).reversed());
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
