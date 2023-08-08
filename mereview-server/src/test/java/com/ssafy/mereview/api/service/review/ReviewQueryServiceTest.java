package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.review.dto.response.ReviewDetailResponse;
import com.ssafy.mereview.api.service.review.dto.response.ReviewResponse;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.repository.MemberRepository;
import com.ssafy.mereview.domain.movie.entity.Genre;
import com.ssafy.mereview.domain.movie.entity.Movie;
import com.ssafy.mereview.domain.movie.repository.GenreRepository;
import com.ssafy.mereview.domain.movie.repository.MovieGenreRepository;
import com.ssafy.mereview.domain.movie.repository.MovieRepository;
import com.ssafy.mereview.domain.review.entity.Review;
import com.ssafy.mereview.domain.review.entity.ReviewEvaluation;
import com.ssafy.mereview.domain.review.entity.ReviewEvaluationType;
import com.ssafy.mereview.domain.review.repository.command.ReviewEvaluationRepository;
import com.ssafy.mereview.domain.review.repository.command.ReviewRepository;
import com.ssafy.mereview.domain.review.repository.dto.SearchCondition;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.NoSuchElementException;

import static com.ssafy.mereview.common.util.SizeConstants.PAGE_SIZE;
import static com.ssafy.mereview.domain.review.entity.MovieEvaluationType.LIKE;
import static com.ssafy.mereview.domain.review.entity.ReviewEvaluationType.FUN;
import static com.ssafy.mereview.domain.review.entity.ReviewEvaluationType.USEFUL;
import static org.assertj.core.api.Assertions.*;

@Slf4j
@TestMethodOrder(MethodOrderer.DisplayName.class)
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

    @BeforeEach
    void setUp() {
        createReviews();
        List<ReviewEvaluation> evaluations = reviewEvaluationRepository.findAll();
        for (ReviewEvaluation evaluation : evaluations) {
            log.debug("evaluation: {}", evaluation);
        }
    }

    @AfterEach
    void tearDown() {
        reviewEvaluationRepository.deleteAllInBatch();
        reviewRepository.deleteAllInBatch();
        movieGenreRepository.deleteAllInBatch();
        genreRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
        movieRepository.deleteAllInBatch();
    }

    @DisplayName("1. 검색 조건 없이 모든 리뷰를 조회한다.")
    @Test
    void searchReviewsWithoutSearchCondition() {
        // given
        SearchCondition condition = new SearchCondition("", "", "", "");
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);

        List<Review> reviews = reviewRepository.findAll();
        for (Review review : reviews) {
            log.debug("review: {}", review.toString());
        }

        // when
        List<ReviewResponse> responses = reviewQueryService.searchByCondition(condition, pageRequest);

        for (ReviewResponse response : responses) {
            log.debug("response: {}", response);
        }

        // then
        assertThat(responses).hasSize(3)
                .extracting("movieTitle", "reviewTitle", "hits", "highlight")
                .containsExactlyInAnyOrder(
                        tuple("영화제목", "그냥 제목1", 0, "그냥 한줄평1"),
                        tuple("영화제목", "테스트 제목2", 20, "테스트 한줄평2"),
                        tuple("영화제목", "테스트 제목1", 0, "테스트 한줄평1")
                );

    }

    @DisplayName("2. 제목이 일치하는 모든 리뷰를 조회한다.")
    @Test
    void searchReviewsByTitle() {
        // given
        SearchCondition condition = new SearchCondition("테스트", "", "", "");
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);

        // when
        List<ReviewResponse> responses = reviewQueryService.searchByCondition(condition, pageRequest);

        // then
        assertThat(responses).hasSize(2)
                .extracting("movieTitle", "reviewTitle", "hits", "highlight")
                .containsExactlyInAnyOrder(
                        tuple("영화제목", "테스트 제목2", 20, "테스트 한줄평2"),
                        tuple("영화제목", "테스트 제목1", 0, "테스트 한줄평1")
                );

    }

    @DisplayName("3. 내용이 일치하는 모든 리뷰를 조회한다.")
    @Test
    void searchReviewsByContent() {
        // given
        SearchCondition condition = new SearchCondition("", "테스트", "", "");
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);

        // when
        List<ReviewResponse> responses = reviewQueryService.searchByCondition(condition, pageRequest);

        // then
        assertThat(responses).hasSize(2)
                .extracting("movieTitle", "reviewTitle", "hits", "highlight")
                .containsExactlyInAnyOrder(
                        tuple("영화제목", "테스트 제목2", 20, "테스트 한줄평2"),
                        tuple("영화제목", "테스트 제목1", 0, "테스트 한줄평1")
                );

    }

    @DisplayName("4. 모든 리뷰를 조회수 순으로 조회한다.")
    @Test
    void searchReviewsOrderByHits() {
        // given
        SearchCondition condition = new SearchCondition("", "", "hits", "");
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);

        // when
        List<ReviewResponse> responses = reviewQueryService.searchByCondition(condition, pageRequest);

        // then
        assertThat(responses).hasSize(3)
                .extracting("movieTitle", "reviewTitle", "hits", "highlight")
                .containsExactly(
                        tuple("영화제목", "테스트 제목2", 20, "테스트 한줄평2"),
                        tuple("영화제목", "테스트 제목1", 0, "테스트 한줄평1"),
                        tuple("영화제목", "그냥 제목1", 0, "그냥 한줄평1")
                );

    }

    @DisplayName("5. 모든 리뷰를 재밌어요 순으로 조회한다.")
    @Test
    void searchReviewsOrderByFunCount() {
        // given
        SearchCondition condition = new SearchCondition("", "", "FUN", "");
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);

        // when
        List<ReviewResponse> responses = reviewQueryService.searchByCondition(condition, pageRequest);
        for (ReviewResponse response : responses) {
            log.debug("response: {}", response);
        }

        // then
        assertThat(responses).hasSize(3)
                .extracting("movieTitle", "reviewTitle", "hits", "highlight", "funCount")
                .containsExactlyInAnyOrder(
                        tuple("영화제목", "테스트 제목1", 0, "테스트 한줄평1", 1),
                        tuple("영화제목", "그냥 제목1", 0, "그냥 한줄평1", 0),
                        tuple("영화제목", "테스트 제목2", 20, "테스트 한줄평2", 0)
                        );

    }

    @DisplayName("6. 모든 리뷰를 유용해요 순으로 조회한다.")
    @Test
    void searchReviewsOrderByUsefulCount() {
        // given
        SearchCondition condition = new SearchCondition("", "", "USEFUL", "");
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);

        // when
        List<ReviewResponse> responses = reviewQueryService.searchByCondition(condition, pageRequest);

        // then
        assertThat(responses).hasSize(3)
                .extracting("movieTitle", "reviewTitle", "hits", "highlight", "usefulCount")
                .containsExactlyInAnyOrder(
                        tuple("영화제목", "그냥 제목1", 0, "그냥 한줄평1", 1),
                        tuple("영화제목", "테스트 제목1", 0, "테스트 한줄평1", 0),
                        tuple("영화제목", "테스트 제목2", 20, "테스트 한줄평2", 0)
                        );

    }

    @DisplayName("7. 제목, 내용 중 어느것도 검색어와 일치하는 것이 없는 경우를 확인한다.")
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

    @DisplayName("8. 존재하는 리뷰를 리뷰의 id 값으로 조회한다.")
    @Test
    void searchExistingReviewById() {
        // given
        Review review = reviewRepository.findAll().stream().findAny().orElseThrow(NoSuchElementException::new);
        Long reviewId = review.getId();
        Long memberId = review.getMember().getId();

        // when
        ReviewDetailResponse response = reviewQueryService.searchById(reviewId, memberId);
        log.debug("response: {}", response);

        // then
        assertThat(response).isNotNull();
    }

    @DisplayName("9. 존재하지 않는 리뷰를 리뷰의 id 값으로 조회한다.")
    @Test
    void searchNotExistingReviewById() {
        // given
        Long reviewId = 234L;
        Long memberId = 234L;

        // when // then
        assertThatThrownBy(() -> reviewQueryService.searchById(reviewId, memberId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 리뷰입니다.");
    }

    /**
     *  private methods
     */

    private Member createMember() {
        Member member = Member.builder()
                .email("test@test.com")
                .password("123456")
                .build();
        return memberRepository.save(member);
    }

    private Movie createMovie(int id) {
        Movie movie = Movie.builder()
                .movieContentId(id)
                .title("영화제목")
                .build();
        return movieRepository.save(movie);
    }

    private Genre createGenre() {
        Genre genre = Genre.builder()
                .genreName("name")
                .isUsing(true)
                .build();
        return genreRepository.save(genre);
    }

    private void createReviews() {
        Member member = createMember();
        Movie movie = createMovie((int) (Math.random() * 100));
        Genre genre = createGenre();
        Review review1 = createReview("테스트 제목1", "테스트 내용1", "테스트 한줄평1", 0, member, movie, genre);
        Review review2 = createReview("테스트 제목2", "테스트 내용2", "테스트 한줄평2", 20, member, movie, genre);
        Review review3 = createReview("그냥 제목1", "그냥 내용1", "그냥 한줄평1", 0, member, movie, genre);
        List<Review> reviews = List.of(review1, review2, review3);
        reviewRepository.saveAll(reviews);
        createReviewEvaluation(review1, FUN);
        createReviewEvaluation(review3, USEFUL);
    }

    private static Review createReview(String title, String content, String highlight, int hits, Member member, Movie movie, Genre genre) {
        return Review.builder()
                .title(title)
                .content(content)
                .highlight(highlight)
                .type(LIKE)
                .hits(hits)
                .member(member)
                .movie(movie)
                .genre(genre)
                .build();
    }

    private void createReviewEvaluation(Review review, ReviewEvaluationType type) {
        ReviewEvaluation reviewEvaluation = ReviewEvaluation.builder()
                .type(type)
                .review(review)
                .member(review.getMember())
                .build();
        reviewEvaluationRepository.save(reviewEvaluation);
    }
}