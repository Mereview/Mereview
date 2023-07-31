package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.review.dto.request.KeywordCreateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.request.ReviewCreateServiceRequest;
import com.ssafy.mereview.common.util.file.UploadFile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.ssafy.mereview.domain.review.entity.EvaluationType.LIKE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReviewServiceTest {
    @Autowired
    private ReviewService reviewService;

    @DisplayName("새로운 리뷰를 작성한다.")
    @Test
    void test() {
        // given
        List<KeywordCreateServiceRequest> keywordRequests = new ArrayList<>();
        keywordRequests.add(KeywordCreateServiceRequest.builder()
                .name("키워드1")
                .weight(5)
                .movieId(1L)
                .build());

        UploadFile uploadFile = UploadFile.builder()
                .uploadFileName("xxx.jpg")
                .storeFileName("xxxxxxxxx.jpg")
                .build();

        ReviewCreateServiceRequest request = ReviewCreateServiceRequest.builder()
                .title("테스트 제목")
                .content("테스트 내용")
                .highlight("테스트 한줄평")
                .type(LIKE)
                .movieId(1L)
                .uploadFile(uploadFile)
                .keywordCreateServiceRequests(keywordRequests)
                .build();

        // when
        Long saveId = reviewService.createReview(request);

        // then
        assertThat(saveId).isEqualTo(1L);

        }
}