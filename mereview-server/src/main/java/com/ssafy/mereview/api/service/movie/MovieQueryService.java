package com.ssafy.mereview.api.service.movie;

import com.ssafy.mereview.api.service.member.dto.response.ProfileImageResponse;
import com.ssafy.mereview.api.service.movie.dto.response.GenreResponse;
import com.ssafy.mereview.api.service.movie.dto.response.MovieDetailResponse;
import com.ssafy.mereview.api.service.movie.dto.response.MovieKeywordResponse;
import com.ssafy.mereview.api.service.review.dto.response.BackgroundImageResponse;
import com.ssafy.mereview.api.service.review.dto.response.ReviewResponse;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.entity.ProfileImage;
import com.ssafy.mereview.domain.movie.entity.Movie;
import com.ssafy.mereview.domain.movie.repository.query.MovieQueryRepository;
import com.ssafy.mereview.domain.review.entity.*;
import com.ssafy.mereview.domain.review.repository.query.ReviewEvaluationQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.ssafy.mereview.domain.review.entity.ReviewEvaluationType.*;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MovieQueryService {
    private final MovieQueryRepository movieQueryRepository;

    private final ReviewEvaluationQueryRepository reviewEvaluationQueryRepository;

    public MovieDetailResponse searchById(Long movieId) {
        Movie movie = movieQueryRepository.searchById(movieId);
        if (movie == null) {
            throw new NoSuchElementException("존재하지 않는 영화입니다.");
        }

        return createMovieDetailResponse(movie);
    }

    public Long searchMovieIdByContentId(Integer contentId) {
        Long movieId = movieQueryRepository.searchMovieIdByContentId(contentId);
        if (movieId == null) {
            throw new NoSuchElementException("존재하지 않는 영화입니다.");
        }
        return movieId;
    }

    /**
     * private methods
     */

    private MovieDetailResponse createMovieDetailResponse(Movie movie){

        List<Review> reviews = movie.getReviews();

        Double evaluation = getMovieEvaluation(reviews);

        List<ReviewResponse> topReviewResponses = createTopReviewResponse(reviews);
        List<ReviewResponse> recentReviewResponses = createRecentReviewResponse(reviews);

        List<MovieKeywordResponse> movieKeywordResponses= createMovieKeywordResponse(reviews);

        return MovieDetailResponse.of(movie, evaluation, movieKeywordResponses, topReviewResponses, recentReviewResponses);
    }

    private Double getMovieEvaluation(List<Review> reviews){
        long totalReviews = reviews.size();

        long positiveCount = reviews.stream()
                .filter(review -> review.getType() == MovieRecommendType.YES)
                .count();

        return (double) positiveCount / totalReviews * 100;
    }

    private List<ReviewResponse> createTopReviewResponse(List<Review> reviews){
        List<ReviewResponse> reviewResponses = createReviewResponse(reviews);

        return reviewResponses.stream()
                .sorted(Comparator.comparingInt(response -> {
                    ReviewResponse reviewResponse = (ReviewResponse) response;
                    return reviewResponse.getFunCount() + reviewResponse.getUsefulCount();
                }).reversed())
                .collect(Collectors.toList());
    }

    private List<ReviewResponse> createRecentReviewResponse(List<Review> reviews){
        List<ReviewResponse> reviewResponses = createReviewResponse(reviews);

        Collections.reverse(reviewResponses);

        return reviewResponses;
    }

    private List<ReviewResponse> createReviewResponse(List<Review> reviews){
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

    private ProfileImageResponse getProfileImageResponse(ProfileImage profileImage) {
        if (profileImage == null) {
            return null;
        }
        return ProfileImageResponse.of(profileImage);
    }

    private List<MovieKeywordResponse> createMovieKeywordResponse(List<Review> reviews){
        List<Keyword> keywords = createMovieKeyword(reviews);

        return keywords.stream()
                .collect(Collectors.groupingBy(
                        Keyword::getName,
                        Collectors.summingInt(Keyword::getWeight)
                ))
                .entrySet().stream()
                .map(entry -> MovieKeywordResponse.of(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private List<Keyword> createMovieKeyword(List<Review> reviews){
        return reviews.stream()
                .flatMap(review -> review.getKeywords().stream())
                .collect(Collectors.toList());
    }
}

