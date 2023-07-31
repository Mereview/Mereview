package com.ssafy.mereview.api.controller.review;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.mereview.api.controller.review.dto.request.KeywordCreateRequest;
import com.ssafy.mereview.api.controller.review.dto.request.ReviewCreateRequest;
import com.ssafy.mereview.api.service.review.ReviewService;
import com.ssafy.mereview.common.util.file.FileExtensionFilter;
import com.ssafy.mereview.common.util.file.FileStore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.ssafy.mereview.domain.review.entity.EvaluationType.LIKE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReviewController.class)
class ReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private FileStore fileStore;

    @MockBean
    private FileExtensionFilter fileExtensionFilter;

    @DisplayName("새로운 리뷰를 작성한다.")
    @Test
    void test() throws Exception {
        // given
        List<KeywordCreateRequest> keywordRequests = new ArrayList<>();
        keywordRequests.add(KeywordCreateRequest.builder()
                .name("키워드1")
                .weight(5)
                .movieId(1L)
                .build());

        MockMultipartFile file = new MockMultipartFile("test1", "test1.PNG", MediaType.IMAGE_PNG_VALUE, "test1".getBytes());

        ReviewCreateRequest request = ReviewCreateRequest.builder()
                .title("테스트 제목")
                .content("테스트 내용")
                .highlight("테스트 한줄평")
                .type(LIKE)
                .movieId(1L)
                .keywordRequests(keywordRequests)
                .build();
        String requestJson = objectMapper.writeValueAsString(request);
        MockMultipartFile review = new MockMultipartFile("request", "request",
                "application/json",
                requestJson.getBytes(StandardCharsets.UTF_8));

        // when // then
        mockMvc.perform(
                        multipart("/review/api/v1")
                                .file("file", file.getBytes())
                                .file(review)
                )
                .andDo(print())
                .andReturn();

    }
}