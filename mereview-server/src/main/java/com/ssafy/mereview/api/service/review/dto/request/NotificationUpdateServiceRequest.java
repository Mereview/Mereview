package com.ssafy.mereview.api.service.review.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationUpdateServiceRequest {
    private Long memberId;
    private Long reviewId;

    @Builder
    public NotificationUpdateServiceRequest(Long memberId, Long reviewId) {
        this.memberId = memberId;
        this.reviewId = reviewId;
    }
}
