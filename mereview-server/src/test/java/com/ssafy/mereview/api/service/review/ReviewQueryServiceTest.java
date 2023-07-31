package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.review.dto.response.ReviewResponse;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.repository.MemberRepository;
import com.ssafy.mereview.domain.movie.entity.Genre;
import com.ssafy.mereview.domain.movie.entity.Movie;
import com.ssafy.mereview.domain.movie.entity.MovieGenre;
import com.ssafy.mereview.domain.movie.repository.GenreRepository;
import com.ssafy.mereview.domain.movie.repository.MovieGenreRepository;
import com.ssafy.mereview.domain.movie.repository.MovieRepository;
import com.ssafy.mereview.domain.review.entity.Review;
import com.ssafy.mereview.domain.review.entity.ReviewLike;
import com.ssafy.mereview.domain.review.entity.ReviewLikeType;
import com.ssafy.mereview.domain.review.repository.ReviewLikeRepository;
import com.ssafy.mereview.domain.review.repository.ReviewRepository;
import com.ssafy.mereview.domain.review.repository.dto.SearchCondition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ssafy.mereview.common.util.SizeConstants.PAGE_SIZE;
import static com.ssafy.mereview.domain.review.entity.EvaluationType.LIKE;
import static com.ssafy.mereview.domain.review.entity.ReviewLikeType.FUN;
import static com.ssafy.mereview.domain.review.entity.ReviewLikeType.USEFUL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@Transactional
@SpringBootTest
class ReviewQueryServiceTest {
    @Autowired
    private ReviewQueryService reviewQueryService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MovieGenreRepository movieGenreRepository;

    @Autowired
    private ReviewLikeRepository reviewLikeRepository;

    @AfterEach
    void tearDown() {
        reviewLikeRepository.deleteAllInBatch();
        reviewRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
        movieRepository.deleteAllInBatch();
    }

    @DisplayName("검색 조건 없이 모든 리뷰를 조회한다.")
    @Test
    void searchReviewsWithoutSearchCondition() {
        // given
        given();
        SearchCondition condition = new SearchCondition("", "", "");
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);

        // when
        List<ReviewResponse> responses = reviewQueryService.searchByCondition(condition, pageRequest);

        // then
        assertThat(responses).hasSize(3)
                .extracting("reviewId", "memberId", "movieId", "movieTitle", "reviewTitle", "hits", "highlight")
                .containsExactly(
                        tuple(3L, 1L, 1L, "영화제목", "그냥 제목1", 0, "그냥 한줄평1"),
                        tuple(2L, 1L, 1L, "영화제목", "테스트 제목2", 20, "테스트 한줄평2"),
                        tuple(1L, 1L, 1L, "영화제목", "테스트 제목1", 0, "테스트 한줄평1")
                );

    }

    @DisplayName("제목이 일치하는 모든 리뷰를 조회한다.")
    @Test
    void searchReviewsByTitle() {
        // given
        given();

        SearchCondition condition = new SearchCondition("테스트", "", "");
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);

        // when
        List<ReviewResponse> responses = reviewQueryService.searchByCondition(condition, pageRequest);

        // then
        assertThat(responses).hasSize(2)
                .extracting("reviewId", "memberId", "movieId", "movieTitle", "reviewTitle", "hits", "highlight")
                .containsExactly(
                        tuple(2L, 1L, 1L, "영화제목", "테스트 제목2", 20, "테스트 한줄평2"),
                        tuple(1L, 1L, 1L, "영화제목", "테스트 제목1", 0, "테스트 한줄평1")
                );

    }

    @DisplayName("내용이 일치하는 모든 리뷰를 조회한다.")
    @Test
    void searchReviewsByContent() {
        // given
        given();

        SearchCondition condition = new SearchCondition("", "테스트", "");
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);

        // when
        List<ReviewResponse> responses = reviewQueryService.searchByCondition(condition, pageRequest);

        // then
        assertThat(responses).hasSize(2)
                .extracting("reviewId", "memberId", "movieId", "movieTitle", "reviewTitle", "hits", "highlight")
                .containsExactly(
                        tuple(2L, 1L, 1L, "영화제목", "테스트 제목2", 20, "테스트 한줄평2"),
                        tuple(1L, 1L, 1L, "영화제목", "테스트 제목1", 0, "테스트 한줄평1")
                );

    }

    @DisplayName("모든 리뷰를 조회수 순으로 조회한다.")
    @Test
    void searchReviewsOrderByHits() {
        // given
        given();

        SearchCondition condition = new SearchCondition("", "", "hits");
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);

        // when
        List<ReviewResponse> responses = reviewQueryService.searchByCondition(condition, pageRequest);

        // then
        assertThat(responses).hasSize(3)
                .extracting("reviewId", "memberId", "movieId", "movieTitle", "reviewTitle", "hits", "highlight")
                .containsExactly(
                        tuple(2L, 1L, 1L, "영화제목", "테스트 제목2", 20, "테스트 한줄평2"),
                        tuple(1L, 1L, 1L, "영화제목", "테스트 제목1", 0, "테스트 한줄평1"),
                        tuple(3L, 1L, 1L, "영화제목", "그냥 제목1", 0, "그냥 한줄평1")
                );

    }

    @DisplayName("모든 리뷰를 재밌어요 순으로 조회한다.")
    @Test
    void searchReviewsOrderByFunCount() {
        // given
        given();
        createReviewLike(FUN, 1L);
        createReviewLike(USEFUL, 3L);

        SearchCondition condition = new SearchCondition("", "", "FUN");
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);

        // when
        List<ReviewResponse> responses = reviewQueryService.searchByCondition(condition, pageRequest);

        // then
        assertThat(responses).hasSize(3)
                .extracting("reviewId", "memberId", "movieId", "movieTitle", "reviewTitle", "hits", "highlight", "funCount")
                .containsExactly(
                        tuple(1L, 1L, 1L, "영화제목", "테스트 제목1", 0, "테스트 한줄평1", 1),
                        tuple(3L, 1L, 1L, "영화제목", "그냥 제목1", 0, "그냥 한줄평1", 0),
                        tuple(2L, 1L, 1L, "영화제목", "테스트 제목2", 20, "테스트 한줄평2", 0)
                );

    }

    @DisplayName("모든 리뷰를 유용해요 순으로 조회한다.")
    @Test
    void searchReviewsOrderByUsefulCount() {
        // given
        given();
        createReviewLike(FUN, 1L);
        createReviewLike(USEFUL, 3L);

        SearchCondition condition = new SearchCondition("", "", "USEFUL");
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);

        // when
        List<ReviewResponse> responses = reviewQueryService.searchByCondition(condition, pageRequest);

        // then
        assertThat(responses).hasSize(3)
                .extracting("reviewId", "memberId", "movieId", "movieTitle", "reviewTitle", "hits", "highlight", "usefulCount")
                .containsExactly(
                        tuple(3L, 1L, 1L, "영화제목", "그냥 제목1", 0, "그냥 한줄평1", 1),
                        tuple(2L, 1L, 1L, "영화제목", "테스트 제목2", 20, "테스트 한줄평2", 0),
                        tuple(1L, 1L, 1L, "영화제목", "테스트 제목1", 0, "테스트 한줄평1", 0)
                );

    }

    @DisplayName("제목, 내용 중 어느것도 검색어와 일치하는 것이 없는 경우를 확인한다.")
    @Test
    void searchEmptyReviews() {
        // given
        given();

        SearchCondition condition = new SearchCondition("!1!", "!@#!#$ASFDF", "");
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);

        // when
        List<ReviewResponse> responses = reviewQueryService.searchByCondition(condition, pageRequest);

        // then
        assertThat(responses).hasSize(0);

    }

    private void given() {
        createMember();
        createMovie();
        createGenre();
        createMovieGenre();
        createReviews();
    }

    private void createMember() {
        Member member = Member.builder()
                .email("test@test.com")
                .password("123456")
                .build();
        memberRepository.save(member);
    }

    private void createMovie() {
        Movie movie = Movie.builder()
                .movieContentId(1)
                .title("영화제목")
                .build();
        movieRepository.save(movie);
    }

    private void createGenre() {
        Genre genre = Genre.builder()
                .genreName("name")
                .isUsing(true)
                .build();
        genreRepository.save(genre);
    }

    private void createMovieGenre() {
        MovieGenre movieGenre = MovieGenre.builder()
                .movie(Movie.builder().id(1L).build())
                .genre(Genre.builder().id(1L).build())
                .build();
        movieGenreRepository.save(movieGenre);
    }

    private void createReviews() {
        Review review1 = createReview("테스트 제목1", "테스트 내용1", "테스트 한줄평1", 0);
        Review review2 = createReview("테스트 제목2", "테스트 내용2", "테스트 한줄평2", 20);
        Review review3 = createReview("그냥 제목1", "그냥 내용1", "그냥 한줄평1", 0);
        List<Review> reviews = List.of(review1, review2, review3);
        reviewRepository.saveAll(reviews);
    }

    private void createReviewLike(ReviewLikeType fun, long id) {
        ReviewLike reviewLikeFun = ReviewLike.builder()
                .type(fun)
                .review(Review.builder().id(id).build())
                .member(Member.builder().id(1L).build())
                .build();
        reviewLikeRepository.save(reviewLikeFun);
    }

    private static Review createReview(String title, String content, String highlight, int hits) {
        return Review.builder()
                .title(title)
                .content(content)
                .highlight(highlight)
                .type(LIKE)
                .hits(hits)
                .member(Member.builder().id(1L).build())
                .movie(Movie.builder().id(1L).build())
                .genre(Genre.builder().id(1L).build())
                .build();
    }
}