package com.ssafy.mereview.api.controller.review;

import com.ssafy.mereview.api.controller.review.dto.request.ReviewCreateRequest;
import com.ssafy.mereview.api.controller.review.dto.request.ReviewUpdateRequest;
import com.ssafy.mereview.api.service.review.ReviewQueryService;
import com.ssafy.mereview.api.service.review.ReviewService;
import com.ssafy.mereview.api.service.review.dto.response.ReviewDetailResponse;
import com.ssafy.mereview.api.service.review.dto.response.ReviewResponse;
import com.ssafy.mereview.common.response.ApiResponse;
import com.ssafy.mereview.common.response.PageResponse;
import com.ssafy.mereview.common.util.file.FileExtensionFilter;
import com.ssafy.mereview.common.util.file.FileStore;
import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.review.repository.dto.SearchCondition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.ssafy.mereview.common.util.SizeConstants.PAGE_SIZE;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
@RestController
@Api(tags = {"리뷰 관련 API"})
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewQueryService reviewQueryService;
    private final FileStore fileStore;
    private final FileExtensionFilter fileExtFilter;

    @PostMapping
    @ApiOperation(value = "리뷰 생성")
    public ApiResponse<Long> createReview(@Valid @RequestPart(name = "request") ReviewCreateRequest request,
                                          @RequestPart(name = "file", required = false) MultipartFile file) throws IOException {
        log.debug("request: {}", request);

        UploadFile uploadFile = createUploadFile(file);
        log.debug("uploadFile: {}", uploadFile);

        Long saveId = reviewService.create(request.toServiceRequest(uploadFile));
        log.debug("saveId: {}", saveId);

        return ApiResponse.ok(saveId);
    }

    @GetMapping
    @ApiOperation(value = "리뷰 검색")
    public ApiResponse<PageResponse<List<ReviewResponse>>> searchReviews(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "") String content,
            @RequestParam(defaultValue = "") String orderBy,
            @RequestParam(defaultValue = "") String term,
            @RequestParam(defaultValue = "1") Integer pageNumber
    ) {
        SearchCondition condition = createCondition(title, content, term, orderBy);
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, PAGE_SIZE);
        List<ReviewResponse> responses = reviewQueryService.searchByCondition(condition, pageRequest);

        int pageCount = reviewQueryService.calculatePageCount(condition);
        PageResponse<List<ReviewResponse>> pageResponse = new PageResponse<>(responses, pageNumber, PAGE_SIZE, pageCount);

        return ApiResponse.ok(pageResponse);
    }

    @GetMapping("/{reviewId}")
    @ApiOperation(value = "리뷰 상세 검색")
    public ApiResponse<ReviewDetailResponse> searchReview(@PathVariable Long reviewId,
                                                          @RequestParam Long loginMemberId) {
        ReviewDetailResponse response = reviewQueryService.searchById(reviewId, loginMemberId);
        return ApiResponse.ok(response);
    }

    @PutMapping("/{reviewId}")
    @ApiOperation(value = "리뷰 수정")
    public ApiResponse<Long> updateReview(@PathVariable Long reviewId,
                                          @Valid @RequestPart ReviewUpdateRequest request,
                                          @RequestPart(required = false) MultipartFile file) throws IOException {
        log.debug("request: {}", request);

        UploadFile uploadFile = createUploadFile(file);
        log.debug("uploadFile: {}", uploadFile);

        Long updateId = reviewService.update(request.toServiceRequest(reviewId, uploadFile));
        return ApiResponse.ok(updateId);
    }

    @DeleteMapping("/{reviewId}")
    @ApiOperation(value = "리뷰 삭제")
    public ApiResponse<Long> deleteReview(@PathVariable Long reviewId) {
        Long deleteId = reviewService.delete(reviewId);
        return ApiResponse.ok(deleteId);
    }

    /**
     *  private methods
     */

    private UploadFile createUploadFile(MultipartFile file) throws IOException {
        UploadFile uploadFile = null;
        if (file != null && !file.isEmpty()) {
            fileExtFilter.imageFilter(file);
            uploadFile = fileStore.storeFile(file);
        }
        return uploadFile;
    }

    private static SearchCondition createCondition(String title, String content, String term, String orderBy) {
        return SearchCondition.builder()
                .title(title)
                .content(content)
                .term(term)
                .orderBy(orderBy)
                .build();
    }
}
