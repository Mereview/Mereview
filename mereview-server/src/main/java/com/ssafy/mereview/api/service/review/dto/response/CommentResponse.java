package com.ssafy.mereview.api.service.review.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponse {

    private Long commentId;
    private Long memberId;
    private Long nickname;
    private String content;
    private boolean isDone;
    private int likeCount;
    private int dislikeCount;
    private LocalDateTime createdTime;

    @Builder
    public CommentResponse(Long commentId, Long memberId, Long nickname, String content, boolean isDone, int likeCount, int dislikeCount, LocalDateTime createdTime) {
        this.commentId = commentId;
        this.memberId = memberId;
        this.nickname = nickname;
        this.content = content;
        this.isDone = isDone;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.createdTime = createdTime;
    }
}
