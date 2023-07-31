package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.review.dto.response.ReviewResponse;
import com.ssafy.mereview.domain.movie.entity.MovieGenre;
import com.ssafy.mereview.domain.review.entity.Review;
import com.ssafy.mereview.domain.review.entity.ReviewLike;
import com.ssafy.mereview.domain.review.repository.ReviewQueryRepository;
import com.ssafy.mereview.domain.review.repository.dto.SearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.mereview.domain.review.entity.ReviewLikeType.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReviewQueryService {
    private final ReviewQueryRepository reviewQueryRepository;

    public List<ReviewResponse> searchByCondition(SearchCondition condition, Pageable pageable) {
        List<Review> reviews = reviewQueryRepository.searchByCondition(condition, pageable);
        return createReviewResponses(reviews);
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
                        .commentCount(review.getComments().size())
                        .funCount(getFunCount(review.getLikes()))
                        .usefulCount(getUsefulCount(review.getLikes()))
                        .badCount(getBadCount(review.getLikes()))
                        .backgroundImageResponse(review.getBackgroundImage().of())
                        .createdTime(review.getCreatedTime())
                        .build()
                ).collect(Collectors.toList());
    }

    private static List<Long> getGenreIds(Review review) {
        return review.getMovie().getMovieGenres().stream().map(MovieGenre::getId).collect(Collectors.toList());
    }

    private int getFunCount(List<ReviewLike> likes) {
        return (int) likes.stream().filter(like -> like.getType().equals(FUN)).count();
    }

    private int getUsefulCount(List<ReviewLike> likes) {
        return (int) likes.stream().filter(like -> like.getType().equals(USEFUL)).count();
    }

    private int getBadCount(List<ReviewLike> likes) {
        return (int) likes.stream().filter(like -> like.getType().equals(BAD)).count();
    }

    public int getTotalPages(SearchCondition condition) {
        return 0;
    }
}
