package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.review.dto.response.ReviewDetailResponse;
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
import com.ssafy.mereview.domain.review.entity.ReviewEvaluation;
import com.ssafy.mereview.domain.review.repository.ReviewEvaluationRepository;
import com.ssafy.mereview.domain.review.repository.ReviewRepository;
import com.ssafy.mereview.domain.review.repository.dto.SearchCondition;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.NoSuchElementException;

import static com.ssafy.mereview.common.util.SizeConstants.PAGE_SIZE;
import static com.ssafy.mereview.domain.review.entity.EvaluationType.LIKE;
import static com.ssafy.mereview.domain.review.entity.ReviewEvaluationType.FUN;
import static com.ssafy.mereview.domain.review.entity.ReviewEvaluationType.USEFUL;
import static org.assertj.core.api.Assertions.*;

/**
 * 본 테스트코드는 참고하지 말 것
 * 새로 작성할 예정
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    private ReviewEvaluationRepository reviewEvaluationRepository;

//    @After
//    void tearDown() {
//        reviewLikeRepository.deleteAllInBatch();
//        reviewRepository.deleteAllInBatch();
//        movieGenreRepository.deleteAllInBatch();
//        genreRepository.deleteAllInBatch();
//        memberRepository.deleteAllInBatch();
//        movieRepository.deleteAllInBatch();
//    }

    //    @Transactional
    @DisplayName("검색 조건 없이 모든 리뷰를 조회한다.")
    @Order(1)
    @Test
    void searchReviewsWithoutSearchCondition() {
        // given
        Long memberId = createMember();
        Long movieId = createMovie();
        Long genreId = createGenre();
        createMovieGenre();
        createReviews(memberId, movieId, genreId);
        SearchCondition condition = new SearchCondition("", "", "", "");
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
    @Order(2)
    @Test
    void searchReviewsByTitle() {
        // given
        SearchCondition condition = new SearchCondition("테스트", "", "", "");
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
    @Order(3)
    @Test
    void searchReviewsByContent() {
        // given
        SearchCondition condition = new SearchCondition("", "테스트", "", "");
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
    @Order(4)
    @Test
    void searchReviewsOrderByHits() {
        // given
        SearchCondition condition = new SearchCondition("", "", "hits", "");
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
    @Order(5)
    @Test
    void searchReviewsOrderByFunCount() {
        // given
        ReviewEvaluation reviewEvaluationFun = ReviewEvaluation.builder()
                .type(FUN)
                .review(Review.builder().id(1L).build())
                .member(Member.builder().id(1L).build())
                .build();
        reviewEvaluationRepository.save(reviewEvaluationFun);

        SearchCondition condition = new SearchCondition("", "", "FUN", "");
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);

        // when
        List<ReviewResponse> responses = reviewQueryService.searchByCondition(condition, pageRequest);
        for (ReviewResponse response : responses) {
            log.info("response: {}", response);
        }

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
    @Order(6)
    @Test
    void searchReviewsOrderByUsefulCount() {
        // given
        ReviewEvaluation reviewEvaluationUseful = ReviewEvaluation.builder()
                .type(USEFUL)
                .review(Review.builder().id(3L).build())
                .member(Member.builder().id(1L).build())
                .build();
        reviewEvaluationRepository.save(reviewEvaluationUseful);
        SearchCondition condition = new SearchCondition("", "", "USEFUL", "");
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
    @Order(7)
    @Test
    void searchEmptyReviews() {
        // given
        SearchCondition condition = new SearchCondition("!1!", "!@#!#$ASFDF", "", "");
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);

        // when
        List<ReviewResponse> responses = reviewQueryService.searchByCondition(condition, pageRequest);

        // then
        assertThat(responses).hasSize(0);

    }

    @DisplayName("존재하는 리뷰를 리뷰의 id 값으로 조회한다.")
    @Test
    void searchExistingReviewById() {
        // given
        Long reviewId = 1L;

        // when
        ReviewDetailResponse response = reviewQueryService.searchById(reviewId);

        // then
        assertThat(response.getReviewId()).isEqualTo(reviewId);
    }

    @DisplayName("존재하지 않는 리뷰를 리뷰의 id 값으로 조회한다.")
    @Test
    void searchNotExistingReviewById() {
        // given
        Long reviewId = 4L;

        // when // then
        assertThatThrownBy(() -> reviewQueryService.searchById(reviewId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 리뷰입니다.");
    }

    private Long createMember() {
        Member member = Member.builder()
                .email("test@test.com")
                .password("123456")
                .build();
        return memberRepository.save(member).getId();
    }

    private Long createMovie() {
        Movie movie = Movie.builder()
                .movieContentId(1)
                .title("영화제목")
                .build();
        return movieRepository.save(movie).getId();
    }

    private Long createGenre() {
        Genre genre = Genre.builder()
                .genreName("name")
                .isUsing(true)
                .build();
        return genreRepository.save(genre).getId();
    }

    private Long createMovieGenre() {
        MovieGenre movieGenre = MovieGenre.builder()
                .movie(Movie.builder().id(1L).build())
                .genre(Genre.builder().id(1L).build())
                .build();
        return movieGenreRepository.save(movieGenre).getId();
    }

    private void createReviews(Long memberId, Long movieId, Long genreId) {
        Review review1 = createReview("테스트 제목1", "테스트 내용1", "테스트 한줄평1", 0, memberId, movieId, genreId);
        Review review2 = createReview("테스트 제목2", "테스트 내용2", "테스트 한줄평2", 20, memberId, movieId, genreId);
        Review review3 = createReview("그냥 제목1", "그냥 내용1", "그냥 한줄평1", 0, memberId, movieId, genreId);
        List<Review> reviews = List.of(review1, review2, review3);
        reviewRepository.saveAll(reviews);
    }

    private static Review createReview(String title, String content, String highlight, int hits, Long memberId, Long movieId, Long genreId) {
        return Review.builder()
                .title(title)
                .content(content)
                .highlight(highlight)
                .type(LIKE)
                .hits(hits)
                .member(Member.builder().id(memberId).build())
                .movie(Movie.builder().id(movieId).build())
                .genre(Genre.builder().id(genreId).build())
                .build();
    }
}