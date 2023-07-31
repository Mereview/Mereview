package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.review.dto.response.BackgroundImageResponse;
import com.ssafy.mereview.api.service.review.dto.response.ReviewResponse;
import com.ssafy.mereview.domain.movie.entity.MovieGenre;
import com.ssafy.mereview.domain.review.entity.Review;
import com.ssafy.mereview.domain.review.entity.ReviewLike;
import com.ssafy.mereview.domain.review.repository.ReviewQueryRepository;
import com.ssafy.mereview.domain.review.repository.dto.SearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.mereview.common.util.SizeConstants.PAGE_SIZE;
import static com.ssafy.mereview.domain.review.entity.ReviewLikeType.*;

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
            responses.sort(Comparator.comparingInt(ReviewResponse::getFunCount).reversed());
        } else if (condition.getOrderBy().equals("USEFUL")) {
            responses.sort(Comparator.comparingInt(ReviewResponse::getUsefulCount).reversed());
        }
        return responses;
    }

    public int calculatePages(SearchCondition condition) {
        return ((reviewQueryRepository.getTotalPages(condition) - 1) / PAGE_SIZE) + 1;
    }

    private List<ReviewResponse> createReviewResponses(List<Review> reviews) {
        return reviews.stream()
                .map(review -> ReviewResponse.builder()
                        .reviewId(review.getId())
                        .memberId(review.getMember().getId())
                        .nickname(review.getMember().getNickname())
                        .movieId(review.getMovie().getId())
                        .movieTitle(review.getMovie().getTitle())
                        .genreIds(getGenreIds(review))
                        .movieReleaseDate(review.getMovie().getReleaseDate())
                        .reviewTitle(review.getTitle())
                        .hits(review.getHits())
                        .highlight(review.getHighlight())
                        .evaluationType(review.getType())
                        .commentCount(getCommentCount(review))
                        .funCount(getFunCount(review.getLikes()))
                        .usefulCount(getUsefulCount(review.getLikes()))
                        .badCount(getBadCount(review.getLikes()))
                        .backgroundImageResponse(getBackgroundImageResponse(review))
                        .createdTime(review.getCreatedTime())
                        .build()
                ).collect(Collectors.toList());
    }

    private static List<Long> getGenreIds(Review review) {
        if (review.getMovie() != null && review.getMovie().getMovieGenres() != null) {
            return review.getMovie().getMovieGenres().stream().map(MovieGenre::getId).collect(Collectors.toList());
        }
        return new ArrayList<>();
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

    private int getFunCount(List<ReviewLike> likes) {
        if (likes != null) {
            return (int) likes.stream().filter(like -> like.getType().equals(FUN)).count();
        }
        return 0;
    }

    private int getUsefulCount(List<ReviewLike> likes) {
        if (likes != null) {
            return (int) likes.stream().filter(like -> like.getType().equals(USEFUL)).count();
        }
        return 0;
    }

    private int getBadCount(List<ReviewLike> likes) {
        if (likes != null) {
            return (int) likes.stream().filter(like -> like.getType().equals(BAD)).count();
        }
        return 0;
    }
}
