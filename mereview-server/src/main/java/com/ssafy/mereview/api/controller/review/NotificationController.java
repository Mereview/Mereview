package com.ssafy.mereview.api.controller.review;

import com.ssafy.mereview.api.service.review.NotificationService;
import com.ssafy.mereview.api.service.review.ReviewQueryService;
import com.ssafy.mereview.api.service.review.dto.response.NotificationResponse;
import com.ssafy.mereview.api.service.review.dto.response.NotifiedReviewResponse;
import com.ssafy.mereview.common.response.ApiResponse;
import com.ssafy.mereview.common.response.PageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ssafy.mereview.common.util.SizeConstants.PAGE_SIZE;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/reviews/notifications")
@RestController
@Api(tags = {"리뷰 작성 알림 API"})
public class NotificationController {

    private final NotificationService notificationService;
    private final ReviewQueryService reviewQueryService;

    @GetMapping
    @ApiOperation(value = "알림 리뷰 조회")
    public ApiResponse<PageResponse<List<NotifiedReviewResponse>>> searchNotifiedReviews(
            @RequestParam Long loginMemberId,
            @RequestParam String status,
            @RequestParam(defaultValue = "1") Integer pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, PAGE_SIZE);
        List<NotifiedReviewResponse> responses = reviewQueryService.searchNotifiedReviews(loginMemberId, status, pageRequest);

        int pageCount = reviewQueryService.calculateNotifiedPageCount(loginMemberId, status);
        PageResponse<List<NotifiedReviewResponse>> pageResponse = new PageResponse<>(responses, pageNumber, PAGE_SIZE, pageCount);

        return ApiResponse.ok(pageResponse);
    }

    @PutMapping("/{notificationId}")
    @ApiOperation(value = "알림 여부 토글")
    public ApiResponse<NotificationResponse> toggleStatus(@PathVariable Long notificationId) {
        NotificationResponse response = notificationService.toggleStatus(notificationId);
        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{notificationId}")
    @ApiOperation(value = "알림 삭제")
    public ApiResponse<Long> deleteNotification(@PathVariable Long notificationId) {
        Long deleteId = notificationService.delete(notificationId);
        return ApiResponse.ok(deleteId);
    }
}
