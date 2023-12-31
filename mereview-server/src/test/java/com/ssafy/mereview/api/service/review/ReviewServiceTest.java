package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.review.dto.request.KeywordCreateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.request.KeywordUpdateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.request.ReviewCreateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.request.ReviewUpdateServiceRequest;
import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.member.entity.Interest;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.repository.MemberInterestQueryRepository;
import com.ssafy.mereview.domain.member.repository.MemberInterestRepository;
import com.ssafy.mereview.domain.member.repository.MemberRepository;
import com.ssafy.mereview.domain.movie.entity.Genre;
import com.ssafy.mereview.domain.movie.entity.Movie;
import com.ssafy.mereview.domain.movie.repository.command.GenreRepository;
import com.ssafy.mereview.domain.movie.repository.command.MovieRepository;
import com.ssafy.mereview.domain.review.entity.Keyword;
import com.ssafy.mereview.domain.review.entity.Review;
import com.ssafy.mereview.domain.review.repository.command.BackgroundImageRepository;
import com.ssafy.mereview.domain.review.repository.command.KeywordRepository;
import com.ssafy.mereview.domain.review.repository.command.NotificationRepository;
import com.ssafy.mereview.domain.review.repository.command.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.ssafy.mereview.domain.review.entity.MovieRecommendType.NO;
import static com.ssafy.mereview.domain.review.entity.MovieRecommendType.YES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@TestMethodOrder(MethodOrderer.DisplayName.class)
@Transactional
@SpringBootTest
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private BackgroundImageRepository backgroundImageRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MemberInterestRepository interestRepository;

    @Autowired
    private MemberInterestQueryRepository interestQueryRepository;

    @Autowired
    private NotificationRepository notificationRepository;



    @DisplayName("1. 새로운 리뷰를 작성한다.")
    @Test
    void createReviewTest() {
        // given
        Genre genre = createGenre();
        Member member = createMember();
        createInterest(member, genre);
        Movie movie = createMovie();

        List<KeywordCreateServiceRequest> keywordRequests = createKeywordCreateRequests(movie.getId());

        UploadFile uploadFile = createUploadFile("xxx.jpg", "xxxxxxxxx.jpg");

        ReviewCreateServiceRequest request = createReviewCreateRequest(keywordRequests, genre.getId(), member.getId(), movie.getId());

        // when
        Long saveId = reviewService.create(request, uploadFile);
        Review review = reviewRepository.findById(saveId)
                .orElseThrow();
        log.debug("review: {}", review);

        // then
        assertThat(saveId).isGreaterThan(0);
        assertThat(keywordRepository.findAll()).hasSize(1);
        assertThat(backgroundImageRepository.findAll()).isNotEmpty();
        assertThat(interestQueryRepository.searchRandomMember(review.getGenre().getId(), request.getMemberId(), 100))
                .hasSize(1);

    }

    @DisplayName("2. 작성된 리뷰를 수정한다.")
    @Test
    void updateExistingReview() {
        // given
        Long reviewId = createReview();

        createKeywords();

        KeywordUpdateServiceRequest keywordRequest = KeywordUpdateServiceRequest.builder()
                .name("수정 키워드1")
                .weight(10)
                .build();
        List<KeywordUpdateServiceRequest> keywordRequests = List.of(keywordRequest);

        UploadFile uploadFile = createUploadFile("수정.jpg", "수정.jpg");

        ReviewUpdateServiceRequest request = ReviewUpdateServiceRequest.builder()
                .reviewId(reviewId)
                .title("수정 제목")
                .content("수정 내용")
                .highlight("수정 한줄평")
                .type(NO)
                .keywordServiceRequests(keywordRequests)
                .uploadFile(uploadFile)
                .build();

        // when
        Long updateId = reviewService.update(request);
        Review updatedReview = reviewRepository.findById(updateId).orElseThrow(NoSuchElementException::new);
        List<Keyword> updatedKeywords = updatedReview.getKeywords();

        // then
        assertThat(updatedReview)
                .extracting("title", "content", "highlight", "type")
                .containsExactly("수정 제목", "수정 내용", "수정 한줄평", NO);
        assertThat(updatedKeywords).hasSize(1);
    }

    @DisplayName("3. 작성되지 않은 리뷰를 수정한다.")
    @Test
    void updateNotExistingReview() {
        // given
        createReview();
        Long reviewId = 235L;

        ReviewUpdateServiceRequest request = ReviewUpdateServiceRequest.builder()
                .reviewId(reviewId)
                .title("수정 제목")
                .content("수정 내용")
                .highlight("수정 한줄평")
                .keywordServiceRequests(List.of())
                .type(NO)
                .build();

        // when // then
        assertThatThrownBy(() -> reviewService.update(request))
                .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("3. 작성된 리뷰를 삭제한다.")
    @Test
    void deleteExistingReview() {
        // given
        Long reviewId = createReview();

        // when
        reviewService.delete(reviewId);
        List<Review> reviews = reviewRepository.findAll();

        // then
        assertThat(reviews).isEmpty();
    }

    @DisplayName("4. 작성되지 않은 리뷰를 삭제한다.")
    @Test
    void deleteNotExistingReview() {
        // given
        createReview();
        Long reviewId = 235L;

        // when // then
        assertThatThrownBy(() -> reviewService.delete(reviewId))
                .isInstanceOf(NoSuchElementException.class);
    }

    /**
     * private methods
     */

    private Movie createMovie() {
        Movie movie = Movie.builder()
                .movieContentId(1)
                .title("영화제목")
                .build();
        return movieRepository.save(movie);
    }

    private Member createMember() {
        Member member = Member.builder()
                .email("test@gmail.com")
                .password("123456")
                .build();
        return memberRepository.save(member);
    }

    private Genre createGenre() {
        Genre genre = Genre.builder()
                .genreNumber(1)
                .genreName("name")
                .isUsing(true)
                .build();
        return genreRepository.save(genre);
    }

    private void createInterest(Member member, Genre genre) {
        Interest interest = Interest.builder()
                .genre(genre)
                .member(member)
                .build();
        interestRepository.save(interest);
    }

    private Long createReview() {
        Review review = Review.builder()
                .title("title")
                .content("content")
                .highlight("highlight")
                .build();
        return reviewRepository.save(review).getId();
    }

    private void createKeywords() {
        Keyword keyword1 = Keyword.builder()
                .name("keyword1")
                .weight(1)
                .build();
        Keyword keyword2 = Keyword.builder()
                .name("keyword1")
                .weight(2)
                .build();
        Keyword keyword3 = Keyword.builder()
                .name("keyword1")
                .weight(3)
                .build();
        List<Keyword> keywords = List.of(keyword1, keyword2, keyword3);
        keywordRepository.saveAll(keywords);
    }

    private List<KeywordCreateServiceRequest> createKeywordCreateRequests(Long movieId) {
        List<KeywordCreateServiceRequest> keywordRequests = new ArrayList<>();
        keywordRequests.add(KeywordCreateServiceRequest.builder()
                .name("키워드1")
                .weight(5)
                .movieId(movieId)
                .build());
        return keywordRequests;
    }

    private UploadFile createUploadFile(String uploadFileName, String storeFileName) {
        return UploadFile.builder()
                .uploadFileName(uploadFileName)
                .storeFileName(storeFileName)
                .build();
    }

    private ReviewCreateServiceRequest createReviewCreateRequest(List<KeywordCreateServiceRequest> keywordRequests, Long genreId, Long memberId, Long movieId) {
        return ReviewCreateServiceRequest.builder()
                .title("테스트 제목")
                .content("테스트 내용")
                .highlight("테스트 한줄평")
                .type(YES)
                .movieId(movieId)
                .memberId(memberId)
                .genreId(genreId)
                .keywordCreateServiceRequests(keywordRequests)
                .build();
    }
}