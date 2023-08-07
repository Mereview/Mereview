package com.ssafy.mereview.api.controller.review;

import com.ssafy.mereview.api.service.review.NotificationService;
import com.ssafy.mereview.api.service.review.dto.response.NotificationResponse;
import com.ssafy.mereview.common.response.ApiResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/reviews/notifications")
@RestController
@Api(tags = {"리뷰 작성 알림 API"})
public class NotificationController {

    private final NotificationService notificationService;

    @PutMapping("/{notificationId}")
    public ApiResponse<NotificationResponse> toggleStatus(@PathVariable Long notificationId) {
        NotificationResponse response = notificationService.toggleStatus(notificationId);
        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{notificationId}")
    public ApiResponse<Long> deleteNotification(@PathVariable Long notificationId) {
        Long deleteId = notificationService.delete(notificationId);
        return ApiResponse.ok(deleteId);
    }
}
