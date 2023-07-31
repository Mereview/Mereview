package com.ssafy.mereview.api.controller.review;

import com.ssafy.mereview.api.controller.review.dto.request.ReviewCreateRequest;
import com.ssafy.mereview.api.service.review.ReviewQueryService;
import com.ssafy.mereview.api.service.review.ReviewService;
import com.ssafy.mereview.api.service.review.dto.response.ReviewResponse;
import com.ssafy.mereview.common.response.ApiResponse;
import com.ssafy.mereview.common.response.PageResponse;
import com.ssafy.mereview.common.util.SizeConstants;
import com.ssafy.mereview.common.util.file.FileExtensionFilter;
import com.ssafy.mereview.common.util.file.FileStore;
import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.review.repository.dto.SearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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

    @GetMapping("/")
    public ApiResponse<PageResponse> searchReviews(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "") String content,
            @RequestParam(defaultValue = "1") Integer pageNumber
    ) {
        SearchCondition condition = SearchCondition.builder()
                .title(title)
                .content(content)
                .build();

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, PAGE_SIZE);
        List<ReviewResponse> responses = reviewQueryService.searchByCondition(condition, pageRequest);

        return ApiResponse.ok(new PageResponse(responses, pageNumber, PAGE_SIZE, reviewQueryService.getTotalPages(condition)));
    }

    private UploadFile createUploadFile(MultipartFile file) throws IOException {
        UploadFile uploadFile = null;
        if (file != null && !file.isEmpty()) {
            fileExtFilter.imageFilter(file);
            uploadFile = fileStore.storeFile(file);
        }
        return uploadFile;
    }
}
