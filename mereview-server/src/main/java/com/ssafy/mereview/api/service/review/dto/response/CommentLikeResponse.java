package com.ssafy.mereview.api.service.review.dto.response;

import com.ssafy.mereview.domain.review.entity.CommentLikeType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentLikeResponse {
    private CommentLikeType commentLikeType;
    private boolean isDone;
    private int likeCount;
    private int dislikeCount;

    @Builder
    public CommentLikeResponse(CommentLikeType commentLikeType, boolean isDone, int likeCount, int dislikeCount) {
        this.commentLikeType = commentLikeType;
        this.isDone = isDone;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }
}
