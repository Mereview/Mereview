package com.ssafy.mereview.api.controller.review;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.mereview.api.controller.review.dto.request.KeywordCreateRequest;
import com.ssafy.mereview.api.controller.review.dto.request.ReviewCreateRequest;
import com.ssafy.mereview.api.service.review.*;
import com.ssafy.mereview.api.service.review.dto.response.ReviewDetailResponse;
import com.ssafy.mereview.api.service.review.dto.response.ReviewResponse;
import com.ssafy.mereview.common.config.SecurityConfig;
import com.ssafy.mereview.common.util.file.FileExtensionFilter;
import com.ssafy.mereview.common.util.file.FileStore;
import com.ssafy.mereview.common.util.jwt.JwtAuthFilter;
import com.ssafy.mereview.domain.review.repository.dto.SearchCondition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.ssafy.mereview.common.util.SizeConstants.PAGE_SIZE;
import static com.ssafy.mereview.domain.review.entity.MovieRecommendType.YES;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = ReviewController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {SecurityConfig.class, JwtAuthFilter.class}))
class ReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReviewService reviewService;
    @MockBean
    private ReviewQueryService reviewQueryService;

    @MockBean
    private CommentService commentService;
    @MockBean
    private ReviewEvaluationService reviewEvaluationService;
    @MockBean
    private CommentLikeService commentLikeService;

    @MockBean
    private FileStore fileStore;
    @MockBean
    private FileExtensionFilter fileExtFilter;

    @DisplayName("새로운 리뷰를 작성한다.")
    @Test
    @WithMockUser
    void createReview() throws Exception {
        // given
        List<KeywordCreateRequest> keywordRequests = createKeywordRequests();

        MockMultipartFile file = createMockMultiFile("test.png");

        ReviewCreateRequest request = crateReviewRequest(keywordRequests);

        // when // then
        String jsonRequest = objectMapper.writeValueAsString(request);
        mockMvc.perform(
                        multipart("/api/reviews")
                                .file(file)
                                .file(createRequestPart(jsonRequest))
                                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andReturn();
    }

    @DisplayName("새로운 리뷰를 작성할 때 키워드는 필수다.")
    @Test
    void createReviewWithoutKeywords() throws Exception {
        // given
        List<KeywordCreateRequest> keywordRequests = List.of();

        MockMultipartFile file = createMockMultiFile("test.png");

        ReviewCreateRequest request = crateReviewRequest(keywordRequests);

        // when // then
        String jsonRequest = objectMapper.writeValueAsString(request);
        mockMvc.perform(
                        multipart("/api/reviews")
                                .file(file)
                                .file(createRequestPart(jsonRequest))
                                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andReturn();
    }

    @DisplayName("리뷰를 검색어 없이 조회한다.")
    @Test
    void searchReviewWithoutCondition() throws Exception {
        // given
        ReviewResponse response1 = createReviewResponse(1L, 1L, 1L);
        ReviewResponse response2 = createReviewResponse(2L, 2L, 2L);
        ReviewResponse response3 = createReviewResponse(3L, 3L, 3L);

        List<ReviewResponse> responses = List.of(response1, response2, response3);
        SearchCondition searchCondition = new SearchCondition("", "", "", "", "", "", "", "", "");
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);

        // stubbing 작업
        given(reviewQueryService.searchByCondition(searchCondition, pageRequest))
                .willReturn(responses);

        // when // then
        mockMvc.perform(
                        get("/api/reviews")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @DisplayName("리뷰를 제목으로 조회한다.")
    @Test
    void searchReviewByTitle() throws Exception {
        // given
        ReviewResponse response1 = createReviewResponse(1L, 1L, 1L);
        ReviewResponse response2 = createReviewResponse(2L, 2L, 2L);
        ReviewResponse response3 = createReviewResponse(3L, 3L, 3L);

        List<ReviewResponse> responses = List.of(response1, response2, response3);
        SearchCondition searchCondition = new SearchCondition("", "", "", "", "", "", "", "", "");
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);

        // stubbing 작업
        given(reviewQueryService.searchByCondition(searchCondition, pageRequest))
                .willReturn(responses);

        // when // then
        mockMvc.perform(
                        get("/api/reviews")
                                .queryParam("title", "테스트")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @DisplayName("리뷰의 id 값으로 리뷰를 조회한다.")
    @Test
    void searchReviewById() throws Exception {
        // given
        Long reviewId = 1L;
        Long memberId = 1L;
        ReviewDetailResponse response = createReviewDetailResponse(reviewId, memberId, 1L);

        // stubbing 작업
        given(reviewQueryService.searchById(reviewId, memberId))
                .willReturn(response);

        // when // then
        mockMvc.perform(
                        get("/api/reviews/" + response.getReviewId() + "?loginMemberId=" + memberId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    private ReviewDetailResponse createReviewDetailResponse(Long reviewId, Long memberId, Long movieId) {
        return ReviewDetailResponse.builder()
                .reviewId(reviewId)
                .memberId(memberId)
                .movieId(movieId)
                .build();
    }

    private static ReviewResponse createReviewResponse(Long reviewId, Long memberId, Long movieId) {
        return ReviewResponse.builder()
                .reviewId(reviewId)
                .reviewTitle("테스트 제목")
                .memberId(memberId)
                .movieId(movieId)
                .build();
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
                .type(String.valueOf(YES))
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