package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.member.MemberService;
import com.ssafy.mereview.api.service.member.dto.request.MemberCreateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.request.ReviewEvaluationServiceRequest;
import com.ssafy.mereview.api.service.review.dto.response.ReviewEvaluationResponse;
import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.entity.MemberTier;
import com.ssafy.mereview.domain.member.repository.MemberRepository;
import com.ssafy.mereview.domain.member.repository.MemberTierRepository;
import com.ssafy.mereview.domain.movie.entity.Genre;
import com.ssafy.mereview.domain.movie.entity.Movie;
import com.ssafy.mereview.domain.movie.repository.GenreRepository;
import com.ssafy.mereview.domain.movie.repository.MovieRepository;
import com.ssafy.mereview.domain.review.entity.Review;
import com.ssafy.mereview.domain.review.repository.query.ReviewEvaluationQueryRepository;
import com.ssafy.mereview.domain.review.repository.command.ReviewEvaluationRepository;
import com.ssafy.mereview.domain.review.repository.command.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.ArrayList;
import java.util.List;

import static com.ssafy.mereview.domain.review.entity.MovieRecommendType.YES;
import static com.ssafy.mereview.domain.review.entity.ReviewEvaluationType.FUN;
import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@SpringBootTest
class ReviewEvaluationServiceTest {

    @Autowired
    private ReviewEvaluationService reviewEvaluationService;

    @Autowired
    private ReviewEvaluationRepository evaluationRepository;

    @Autowired
    private ReviewEvaluationQueryRepository evaluationQueryRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberTierRepository memberTierRepository;

    @BeforeEach
    void setUp() {
        createReviews();
    }

    @AfterEach
    void tearDown() {
        evaluationRepository.deleteAllInBatch();
        reviewRepository.deleteAllInBatch();
        genreRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
        movieRepository.deleteAllInBatch();
    }

    @DisplayName("현재 사용자가 평가를 남긴 적없는 리뷰이면 해당 리뷰에 대한 평가를 저장한다.")
    @Test
    void createReviewEvaluation() {
        // given
        Long reviewId = reviewRepository.findAll().stream().findAny()
                .orElseThrow().getId();
        Long memberId = memberRepository.findAll().stream().findAny()
                .orElseThrow().getId();
        Long genreId = genreRepository.findAll().stream().findAny()
                .orElseThrow().getId();

        ReviewEvaluationServiceRequest request = ReviewEvaluationServiceRequest.builder()
                .reviewId(reviewId)
                .memberId(memberId)
                .genreId(genreId)
                .type(FUN)
                .build();

        // when
        ReviewEvaluationResponse response = reviewEvaluationService.update(request);

        // then
        assertThat(response)
                .extracting("reviewEvaluationType", "isDone", "funCount", "usefulCount", "badCount")
                .containsExactlyInAnyOrder(FUN, true, 1, 0, 0);
    }

    @DisplayName("현재 사용자가 평가를 남긴 적있는 리뷰이면 해당 리뷰에 대한 평가를 삭제한다.")
    @Test
    void deleteReviewEvaluation() {
        // given
        Long reviewId = reviewRepository.findAll().stream().findAny()
                .orElseThrow().getId();
        Long memberId = memberRepository.findAll().stream().findAny()
                .orElseThrow().getId();
        Long genreId = genreRepository.findAll().stream().findAny()
                .orElseThrow().getId();

        ReviewEvaluationServiceRequest request = ReviewEvaluationServiceRequest.builder()
                .reviewId(reviewId)
                .memberId(memberId)
                .genreId(genreId)
                .type(FUN)
                .build();

        evaluationRepository.save(request.toEntity());

        // when
        ReviewEvaluationResponse response = reviewEvaluationService.update(request);

        // then
        assertThat(response)
                .extracting("reviewEvaluationType", "isDone", "funCount", "usefulCount", "badCount")
                .containsExactlyInAnyOrder(FUN, false, 0, 0, 0);
    }

    @Commit
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

    private void createTier(Member member) {
        List<Genre> genres = genreRepository.findAll();
        List<MemberTier> memberTiers = new ArrayList<>();
        genres.forEach(genre -> memberTiers.add(MemberTier.builder().member(member).genre(genre).build()));
        memberTierRepository.saveAll(memberTiers);
    }

    private void createReviews() {
        Member member = createMember();
        Movie movie = createMovie((int) (Math.random() * 100));
        Genre genre = createGenre();
        createTier(member);
        Review review1 = createReview("테스트 제목1", "테스트 내용1", "테스트 한줄평1", 0, member, movie, genre);
        Review review2 = createReview("테스트 제목2", "테스트 내용2", "테스트 한줄평2", 20, member, movie, genre);
        Review review3 = createReview("그냥 제목1", "그냥 내용1", "그냥 한줄평1", 0, member, movie, genre);
        List<Review> reviews = List.of(review1, review2, review3);
        reviewRepository.saveAll(reviews);
    }

    private static Review createReview(String title, String content, String highlight, int hits, Member member, Movie movie, Genre genre) {
        return Review.builder()
                .title(title)
                .content(content)
                .highlight(highlight)
                .type(YES)
                .hits(hits)
                .member(member)
                .movie(movie)
                .genre(genre)
                .build();
    }
}