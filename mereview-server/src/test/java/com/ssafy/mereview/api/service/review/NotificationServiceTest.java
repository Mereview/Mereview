package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.review.dto.request.NotificationUpdateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.response.NotificationResponse;
import com.ssafy.mereview.domain.member.entity.Interest;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.repository.MemberInterestRepository;
import com.ssafy.mereview.domain.member.repository.MemberRepository;
import com.ssafy.mereview.domain.movie.entity.Genre;
import com.ssafy.mereview.domain.movie.entity.Movie;
import com.ssafy.mereview.domain.movie.repository.GenreRepository;
import com.ssafy.mereview.domain.movie.repository.MovieRepository;
import com.ssafy.mereview.domain.review.entity.Notification;
import com.ssafy.mereview.domain.review.entity.Review;
import com.ssafy.mereview.domain.review.repository.command.BackgroundImageRepository;
import com.ssafy.mereview.domain.review.repository.command.KeywordRepository;
import com.ssafy.mereview.domain.review.repository.command.NotificationRepository;
import com.ssafy.mereview.domain.review.repository.command.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static com.ssafy.mereview.domain.review.entity.NotificationStatus.CONFIRMED;
import static org.assertj.core.api.Assertions.*;

@Slf4j
@TestMethodOrder(MethodOrderer.DisplayName.class)
@Transactional
@SpringBootTest
class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

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
    private NotificationRepository notificationRepository;

    @BeforeEach
    void setUp() {
        Genre genre = createGenre();
        Member member = createMember();
        createInterest(genre, member);
        Movie movie = createMovie();
        Review review = createReview(movie, member, genre);
        createNotification(review, member);
    }

    @AfterEach
    void tearDown() {
        notificationRepository.deleteAllInBatch();
        interestRepository.deleteAllInBatch();
        reviewRepository.deleteAllInBatch();
        movieRepository.deleteAllInBatch();
        genreRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("1. 현재 알림의 확인 여부 상태를 변경한다.")
    @Test
    void toggleStatus() {
        // given
        Long memberId = memberRepository.findAll().stream().findAny().orElseThrow().getId();
        Long reviewId = reviewRepository.findAll().stream().findAny().orElseThrow().getId();

        NotificationUpdateServiceRequest request = NotificationUpdateServiceRequest.builder()
                .memberId(memberId)
                .reviewId(reviewId)
                .build();

        // when
        NotificationResponse response = notificationService.toggleStatus(request);

        // then
        assertThat(response).extracting("memberId", "reviewId", "status")
                .containsExactly(memberId, reviewId, CONFIRMED);
    }

    @DisplayName("2. 알림을 삭제한다.")
    @Test
    void deleteNotification() {
        // given
        Long notificationId = notificationRepository.findAll().stream()
                .findAny().orElseThrow().getId();

        // when
        notificationService.delete(notificationId);

        // then
        assertThatThrownBy(() -> notificationService.delete(notificationId))
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

    private void createInterest(Genre genre, Member member) {
        Interest interest = Interest.builder()
                .genre(genre)
                .member(member)
                .build();
        interestRepository.save(interest);
    }

    private Review createReview(Movie movie, Member member, Genre genre) {
        Review review = Review.builder()
                .title("title")
                .content("content")
                .highlight("highlight")
                .movie(movie)
                .member(member)
                .genre(genre)
                .build();
        return reviewRepository.save(review);
    }

    private void createNotification(Review review, Member member) {
        Notification notification = Notification.builder()
                .review(review)
                .member(member)
                .build();
        notificationRepository.save(notification);
    }

}
