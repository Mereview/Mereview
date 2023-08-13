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
import com.ssafy.mereview.domain.review.repository.query.CommentLikeQueryRepository;
import com.ssafy.mereview.domain.review.repository.query.NotificationQueryRepository;
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
import static com.ssafy.mereview.domain.review.entity.CommentLikeType.DISLIKE;
import static com.ssafy.mereview.domain.review.entity.CommentLikeType.LIKE;
import static com.ssafy.mereview.domain.review.entity.ReviewEvaluationType.*;
import static java.util.Comparator.comparingInt;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReviewQueryService {
    private final ReviewQueryRepository reviewQueryRepository;
    private final ReviewEvaluationQueryRepository reviewEvaluationQueryRepository;
    private final NotificationQueryRepository notificationQueryRepository;
    private final CommentLikeQueryRepository commentLikeQueryRepository;

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

    public List<ReviewResponse> searchNotifiedReviews(Long memberId,Pageable pageable) {
        List<Long> reviewIds = notificationQueryRepository.searchReviewIdsByMemberId(memberId);
        List<Review> reviews = reviewQueryRepository.searchNotifiedReviews(reviewIds, pageable);
        return createReviewResponses(reviews);
    }

    public int calculateNotifiedPageCount(Long memberId) {
        List<Long> reviewIds = notificationQueryRepository.searchReviewIdsByMemberId(memberId);
        return ((reviewQueryRepository.getNotifiedTotalPages(reviewIds) - 1) / PAGE_SIZE) + 1;
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
                .movieEvaluatedType(review.getType().getText())
                .hits(review.getHits())
                .backgroundImage(createBackgroundImageResponse(review.getBackgroundImage()))
                .reviewHighlight(review.getHighlight())
                .createdTime(review.getCreatedTime())
                .keywords(getKeywordResponses(review.getKeywords()))
                .isEvaluated(isEvaluated(review.getId(), writeMember.getId()))
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
                .comments(createCommentResponses(review.getComments(), review.getMember().getId()))
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

    private List<CommentResponse> createCommentResponses(List<Comment> comments, Long memberId) {
        return comments.stream()
                .map(comment ->
                        CommentResponse.builder()
                                .commentId(comment.getId())
                                .memberId(comment.getMember().getId())
                                .nickname(comment.getMember().getNickname())
                                .content(comment.getContent())
                                .isDone(checkIsDone(comment, memberId))
                                .likeCount(getCountByType(LIKE, comment.getId()))
                                .dislikeCount(getCountByType(DISLIKE, comment.getId()))
                                .profileImage(getProfileImageResponse(comment.getMember().getProfileImage()))
                                .createdTime(comment.getCreatedTime())
                                .build()
                )
                .collect(Collectors.toList());
    }

    private boolean checkIsDone(Comment comment, Long memberId) {
        Optional<CommentLike> commentLike = commentLikeQueryRepository.searchByCommentAndMember(comment.getId(), memberId);
        return commentLike.isPresent();
    }

    private Integer getCountByType(CommentLikeType commentLikeType, Long commentId) {
        return commentLikeQueryRepository.getCountByCommentIdGroupByType(commentId).getOrDefault(commentLikeType, 0);
    }

    private ProfileImageResponse getProfileImageResponse(ProfileImage profileImage) {
        if (profileImage == null) {
            return null;
        }
        return ProfileImageResponse.of(profileImage);
    }
}
