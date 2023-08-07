package com.ssafy.mereview.api.service.review;


import com.ssafy.mereview.api.service.review.dto.request.CommentLikeServiceRequest;
import com.ssafy.mereview.api.service.review.dto.response.CommentLikeResponse;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.repository.MemberRepository;
import com.ssafy.mereview.domain.movie.entity.Genre;
import com.ssafy.mereview.domain.movie.entity.Movie;
import com.ssafy.mereview.domain.movie.repository.GenreRepository;
import com.ssafy.mereview.domain.movie.repository.MovieRepository;
import com.ssafy.mereview.domain.review.entity.Comment;
import com.ssafy.mereview.domain.review.entity.EvaluationType;
import com.ssafy.mereview.domain.review.entity.Review;
import com.ssafy.mereview.domain.review.repository.CommentLikeQueryRepository;
import com.ssafy.mereview.domain.review.repository.CommentLikeRepository;
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

import static com.ssafy.mereview.domain.review.entity.CommentLikeType.LIKE;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class CommentLikeServiceTest {

    @Autowired
    private CommentLikeService commentLikeService;

    @Autowired
    private CommentLikeRepository commentLikeRepository;

    @Autowired
    private CommentLikeQueryRepository commentLikeQueryRepository;

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
        createComments();
    }

    @AfterEach
    void tearDown() {
        commentLikeRepository.deleteAllInBatch();
        commentRepository.deleteAllInBatch();
        reviewRepository.deleteAllInBatch();
        genreRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
        movieRepository.deleteAllInBatch();
    }

    @DisplayName("현재 사용자가 좋아요/싫어요를 남긴적 없는 댓글이면 해당 댓글에 대한 좋아요/싫어요 를 저장한다.")
    @Test
    void createCommentLike() {
        // given
        Long commentId = commentRepository.findAll().stream().findAny()
                .orElseThrow().getId();
        Long memberId = memberRepository.findAll().stream().findAny()
                .orElseThrow().getId();

        CommentLikeServiceRequest request = CommentLikeServiceRequest.builder()
                .commentId(commentId)
                .memberId(memberId)
                .type(LIKE)
                .build();

        // when
        CommentLikeResponse response = commentLikeService.updateCommentLike(request);

        // then
        assertThat(response)
                .extracting("commentLikeType", "isDone", "likeCount", "dislikeCount")
                .containsExactlyInAnyOrder(LIKE, true, 1, 0);
    }

    @DisplayName("현재 사용자가 좋아요/싫어요를 남긴적 없는 댓글이면 해당 댓글에 대한 좋아요/싫어요 를 저장한다.")
    @Test
    void deleteCommentLike() {
        // given
        Long commentId = commentRepository.findAll().stream().findAny()
                .orElseThrow().getId();
        Long memberId = memberRepository.findAll().stream().findAny()
                .orElseThrow().getId();

        CommentLikeServiceRequest request = CommentLikeServiceRequest.builder()
                .commentId(commentId)
                .memberId(memberId)
                .type(LIKE)
                .build();

        // when
        commentLikeRepository.save(request.toEntity());
        CommentLikeResponse response = commentLikeService.updateCommentLike(request);

        // then
        assertThat(response)
                .extracting("commentLikeType", "isDone", "likeCount", "dislikeCount")
                .containsExactlyInAnyOrder(LIKE, false, 0, 0);
    }

    private void createComments() {
        Member member = createMember();
        Movie movie = createMovie((int) (Math.random() * 100));
        Genre genre = createGenre();
        Review review = createReview("테스트 제목1", "테스트 내용1", "테스트 한줄평1", 0, member, movie, genre);
        Comment comment1 = createComment(member, review, "댓글1");
        Comment comment2 = createComment(member, review, "댓글2");
        Comment comment3 = createComment(member, review, "댓글3");
        List<Comment> comments = List.of(comment1, comment2, comment3);
        commentRepository.saveAll(comments);
    }

    private static Comment createComment(Member member, Review review, String content) {
        return Comment.builder()
                .content(content)
                .review(review)
                .member(member)
                .build();
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

    private Review createReview(String title, String content, String highlight, int hits, Member member, Movie movie, Genre genre) {
        Review review = Review.builder()
                .title(title)
                .content(content)
                .highlight(highlight)
                .type(EvaluationType.LIKE)
                .hits(hits)
                .member(member)
                .movie(movie)
                .genre(genre)
                .build();

        return reviewRepository.save(review);
    }
}