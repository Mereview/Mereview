package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.review.dto.request.KeywordCreateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.request.KeywordUpdateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.request.ReviewCreateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.request.ReviewUpdateServiceRequest;
import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.review.repository.ReviewRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.ssafy.mereview.domain.review.entity.EvaluationType.DISLIKE;
import static com.ssafy.mereview.domain.review.entity.EvaluationType.LIKE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Mocking 필요
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @DisplayName("새로운 리뷰를 작성한다.")
    @Order(1)
    @Test
    void createReview() {
        // given
        List<KeywordCreateServiceRequest> keywordRequests = createKeywordCreateRequests();

        UploadFile uploadFile = createUploadFile("xxx.jpg", "xxxxxxxxx.jpg");

        ReviewCreateServiceRequest request = createReviewCreateRequest(keywordRequests, uploadFile);

        // when
        Long saveId = reviewService.createReview(request);

        // then
        assertThat(saveId).isEqualTo(1L);

    }

    @DisplayName("존재하는 리뷰를 수정한다.")
    @Order(2)
    @Test
    void updateExistingReview() {
        // given
        Long reviewId = 1L;

        KeywordUpdateServiceRequest keywordRequest = KeywordUpdateServiceRequest.builder()
                .name("수정 키워드1")
                .weight(10)
                .build();
        List<KeywordUpdateServiceRequest> keywordRequests = List.of(keywordRequest);

        UploadFile uploadFile = createUploadFile("수정.jpg", "수정.jpg");

        ReviewUpdateServiceRequest request = ReviewUpdateServiceRequest.builder()
                .title("수정 제목")
                .content("수정 내용")
                .highlight("수정 한줄평")
                .type(DISLIKE)
                .keywordServiceRequests(keywordRequests)
                .uploadFile(uploadFile)
                .build();

        // when
        Long updateId = reviewService.update(reviewId, request);

        // then
        assertThat(updateId).isEqualTo(1L);
    }

    @DisplayName("존재하지 않는 리뷰를 수정한다.")
    @Order(3)
    @Test
    void updateNotExistingReview() {
        // given
        Long reviewId = 5L;

        KeywordUpdateServiceRequest keywordRequest = KeywordUpdateServiceRequest.builder()
                .name("수정 키워드1")
                .weight(10)
                .build();
        List<KeywordUpdateServiceRequest> keywordRequests = List.of(keywordRequest);

        UploadFile uploadFile = createUploadFile("수정.jpg", "수정.jpg");

        ReviewUpdateServiceRequest request = ReviewUpdateServiceRequest.builder()
                .title("수정 제목")
                .content("수정 내용")
                .highlight("수정 한줄평")
                .type(DISLIKE)
                .keywordServiceRequests(keywordRequests)
                .uploadFile(uploadFile)
                .build();

        // when // then
        assertThatThrownBy(() -> reviewService.update(reviewId, request))
                .isInstanceOf(NoSuchElementException.class);
    }

    private static List<KeywordCreateServiceRequest> createKeywordCreateRequests() {
        List<KeywordCreateServiceRequest> keywordRequests = new ArrayList<>();
        keywordRequests.add(KeywordCreateServiceRequest.builder()
                .name("키워드1")
                .weight(5)
                .movieId(1L)
                .build());
        return keywordRequests;
    }

    private static UploadFile createUploadFile(String uploadFileName, String storeFileName) {
        return UploadFile.builder()
                .uploadFileName(uploadFileName)
                .storeFileName(storeFileName)
                .build();
    }

    private static ReviewCreateServiceRequest createReviewCreateRequest(List<KeywordCreateServiceRequest> keywordRequests, UploadFile uploadFile) {
        return ReviewCreateServiceRequest.builder()
                .title("테스트 제목")
                .content("테스트 내용")
                .highlight("테스트 한줄평")
                .type(LIKE)
                .movieId(1L)
                .memberId(1L)
                .genreId(1L)
                .uploadFile(uploadFile)
                .keywordCreateServiceRequests(keywordRequests)
                .build();
    }
}