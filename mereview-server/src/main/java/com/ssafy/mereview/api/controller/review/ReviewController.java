package com.ssafy.mereview.api.controller.review;

import com.ssafy.mereview.api.controller.review.dto.request.CommentCreateRequest;
import com.ssafy.mereview.api.controller.review.dto.request.ReviewCreateRequest;
import com.ssafy.mereview.api.controller.review.dto.request.ReviewUpdateRequest;
import com.ssafy.mereview.api.service.review.CommentService;
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
@RequestMapping("/reviews")
@RestController
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewQueryService reviewQueryService;
    private final CommentService commentService;

    private final FileStore fileStore;
    private final FileExtensionFilter fileExtFilter;

    @PostMapping()
    public ApiResponse<Long> createReview(@Valid @RequestPart(name = "request") ReviewCreateRequest request,
                                          @RequestPart(name = "file", required = false) MultipartFile file) throws IOException {
        log.debug("request: {}", request);

        UploadFile uploadFile = createUploadFile(file);
        log.debug("uploadFile: {}", uploadFile);

        Long saveId = reviewService.createReview(request.toServiceRequest(uploadFile));
        log.debug("saveId: {}", saveId);
        return ApiResponse.ok(saveId);
    }

    @GetMapping("")
    public ApiResponse<PageResponse<List<ReviewResponse>>> searchReviews(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "") String content,
            @RequestParam(defaultValue = "1") Integer pageNumber
    ) {
        SearchCondition condition = createCondition(title, content);

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, PAGE_SIZE);
        List<ReviewResponse> responses = reviewQueryService.searchByCondition(condition, pageRequest);
        int pages = reviewQueryService.calculatePages(condition);
        PageResponse<List<ReviewResponse>> pageResponse = new PageResponse<>(responses, pageNumber, PAGE_SIZE, pages);

        return ApiResponse.ok(pageResponse);
    }

    @GetMapping("/{reviewId}")
    public ApiResponse<ReviewDetailResponse> searchReview(@PathVariable Long reviewId) {
        ReviewDetailResponse response = reviewQueryService.searchById(reviewId);
        return ApiResponse.ok(response);
    }

    @PutMapping("/{reviewId}")
    public ApiResponse<Long> updateReview(@PathVariable Long reviewId,
                                          @Valid @RequestPart ReviewUpdateRequest request,
                                          @RequestPart(required = false) MultipartFile file) throws IOException {
        log.debug("request: {}", request);

        UploadFile uploadFile = createUploadFile(file);
        log.debug("uploadFile: {}", uploadFile);

        Long updateId = reviewService.update(reviewId, request.toServiceRequest(uploadFile));
        return ApiResponse.ok(updateId);
    }

    @DeleteMapping("/{reviewId}")
    public ApiResponse<Long> deleteReview(@PathVariable Long reviewId) {
        Long deleteId = reviewService.delete(reviewId);
        return ApiResponse.ok(deleteId);
    }

    @PostMapping("/comments")
    public ApiResponse<Long> createReviewComment(@Valid @RequestBody CommentCreateRequest request) {
        Long saveId = commentService.save(request.toServiceRequest());
        return ApiResponse.ok(saveId);
    }

    @PutMapping("/comments/{commentId}")
    public ApiResponse<Long> updateReviewComment(@PathVariable Long commentId, @Valid @RequestBody CommentUpdateRequest request) {
        Long updateId = commentService.update(commentId, request.toServiceRequest());
        return ApiResponse.ok(updateId);
    }

    private UploadFile createUploadFile(MultipartFile file) throws IOException {
        UploadFile uploadFile = null;
        if (file != null && !file.isEmpty()) {
            fileExtFilter.imageFilter(file);
            uploadFile = fileStore.storeFile(file);
        }
        return uploadFile;
    }

    private static SearchCondition createCondition(String title, String content) {
        return SearchCondition.builder()
                .title(title)
                .content(content)
                .build();
    }
}
