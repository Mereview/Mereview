package com.ssafy.mereview.api.controller.review.dto;

import com.ssafy.mereview.api.service.review.dto.request.NotificationUpdateServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class NotificationUpdateRequest {
    @NotNull
    private Long memberId;

    @NotNull
    private Long reviewId;

    @Builder
    public NotificationUpdateRequest(Long memberId, Long reviewId) {
        this.memberId = memberId;
        this.reviewId = reviewId;
    }

    public NotificationUpdateServiceRequest toServiceRequest() {
        return NotificationUpdateServiceRequest.builder()
                .memberId(memberId)
                .reviewId(reviewId)
                .build();
    }
}
