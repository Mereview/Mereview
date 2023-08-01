package com.ssafy.mereview.api.service.review.dto.response;

import com.ssafy.mereview.domain.review.entity.ReviewLikeType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewLikeResponse {

    private Long reviewLikeId;
    private ReviewLikeType reviewLikeType;

    @Builder
    public ReviewLikeResponse(Long reviewLikeId, ReviewLikeType reviewLikeType) {
        this.reviewLikeId = reviewLikeId;
        this.reviewLikeType = reviewLikeType;
    }
}
