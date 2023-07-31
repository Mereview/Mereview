package com.ssafy.mereview.api.controller.review;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.mereview.api.controller.review.dto.request.KeywordCreateRequest;
import com.ssafy.mereview.api.controller.review.dto.request.ReviewCreateRequest;
import com.ssafy.mereview.api.service.member.UserDetailsServiceImpl;
import com.ssafy.mereview.api.service.review.ReviewService;
import com.ssafy.mereview.common.util.file.FileExtensionFilter;
import com.ssafy.mereview.common.util.file.FileStore;
import com.ssafy.mereview.common.util.jwt.JwtAuthFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.ssafy.mereview.domain.review.entity.EvaluationType.LIKE;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
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

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @DisplayName("새로운 리뷰를 작성한다.")
    @Test
    void createReview() throws Exception {
        // given
        List<KeywordCreateRequest> keywordRequests = createKeywordRequests();

        MockMultipartFile file = createMockMultiFile("test.png");

        ReviewCreateRequest request = crateReviewRequest(keywordRequests);

        // when // then
        String jsonRequest = objectMapper.writeValueAsString(request);
        mockMvc.perform(
                        multipart("/review/api/v1")
                                .file(file)
                                .file(createRequestPart(jsonRequest))
                                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    private static List<KeywordCreateRequest> createKeywordRequests() {
        List<KeywordCreateRequest> keywordRequests = new ArrayList<>();
        keywordRequests.add(KeywordCreateRequest.builder()
                .name("키워드1")
                .weight(5)
                .movieId(1L)
                .build());
        return keywordRequests;
    }

    private static ReviewCreateRequest crateReviewRequest(List<KeywordCreateRequest> keywordRequests) {
        return ReviewCreateRequest.builder()
                .title("테스트 제목")
                .content("테스트 내용")
                .highlight("테스트 한줄평")
                .type(LIKE)
                .memberId(1L)
                .genreId(1L)
                .movieId(1L)
                .keywordRequests(keywordRequests)
                .build();
    }

    private static MockMultipartFile createMockMultiFile(String originalFilename) {
        return new MockMultipartFile("file", originalFilename,
                IMAGE_PNG_VALUE, "test1".getBytes());
    }

    private static MockMultipartFile createRequestPart(String jsonRequest) {
        return new MockMultipartFile("request", "request.json", APPLICATION_JSON_VALUE, jsonRequest.getBytes(UTF_8));
    }
}