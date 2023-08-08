package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.review.dto.request.CommentCreateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.request.CommentUpdateServiceRequest;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.repository.MemberRepository;
import com.ssafy.mereview.domain.movie.entity.Genre;
import com.ssafy.mereview.domain.movie.entity.Movie;
import com.ssafy.mereview.domain.movie.repository.GenreRepository;
import com.ssafy.mereview.domain.movie.repository.MovieRepository;
import com.ssafy.mereview.domain.review.entity.Comment;
import com.ssafy.mereview.domain.review.entity.Review;
import com.ssafy.mereview.domain.review.repository.CommentRepository;
import com.ssafy.mereview.domain.review.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

import static com.ssafy.mereview.domain.review.entity.MovieEvaluationType.LIKE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;


    @BeforeEach
    void setUp() {
        createReviews();
    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAllInBatch();
        reviewRepository.deleteAllInBatch();
        genreRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
        movieRepository.deleteAllInBatch();
    }

    @DisplayName("댓글을 작성한다.")
    @Test
    void createComment() {
        // given
        Long reviewId = reviewRepository.findAll().stream().findAny()
                .orElseThrow().getId();
        Long memberId = memberRepository.findAll().stream().findAny()
                .orElseThrow().getId();

        CommentCreateServiceRequest request = CommentCreateServiceRequest.builder()
                .memberId(memberId)
                .reviewId(reviewId)
                .content("content")
                .build();

        // when
        Long commentId = commentService.save(request);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow();

        // then
        assertThat(comment.getContent()).isEqualTo("content");
    }

    @DisplayName("댓글을 수정한다.")
    @Test
    void updateComment() {
        // given
        Review review = reviewRepository.findAll().stream().findAny()
                .orElseThrow();
        Member member = memberRepository.findAll().stream().findAny()
                .orElseThrow();

        Comment comment = Comment.builder()
                .content("댓글")
                .member(member)
                .review(review)
                .build();
        commentRepository.save(comment);

        CommentUpdateServiceRequest request = CommentUpdateServiceRequest.builder()
                .commentId(comment.getId())
                .content("수정 댓글")
                .build();
        
        // when
        Long commentId = commentService.update(request);
        Comment updatedComment = commentRepository.findById(commentId).orElseThrow();
        // then
        assertThat(updatedComment.getContent())
                .isEqualTo("수정 댓글");
    }

    @DisplayName("작성되어 있는 댓글을 삭제한다.")
    @Test
    void deleteComment() {
        // given
        Review review = reviewRepository.findAll().stream().findAny()
                .orElseThrow();
        Member member = memberRepository.findAll().stream().findAny()
                .orElseThrow();

        Comment comment = Comment.builder()
                .content("댓글")
                .member(member)
                .review(review)
                .build();
        Long commentId = commentRepository.save(comment).getId();

        // when
        commentService.delete(commentId);
        List<Comment> comments = commentRepository.findAll();

        // then
        assertThat(comments).isEmpty();
    }

    @DisplayName("작성되어있지 않은 댓글을 삭제한다.")
    @Test
    void deleteCommentNotExists() {
        // given
        Long commentId = 5L;

        // when // then
        assertThatThrownBy(() -> commentService.delete(commentId))
                .isInstanceOf(NoSuchElementException.class);
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
    }

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
}