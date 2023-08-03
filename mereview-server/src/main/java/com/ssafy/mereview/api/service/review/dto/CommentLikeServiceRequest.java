package com.ssafy.mereview.api.service.review.dto;

import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.review.entity.Comment;
import com.ssafy.mereview.domain.review.entity.CommentLike;
import com.ssafy.mereview.domain.review.entity.CommentLikeType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentLikeServiceRequest {

    private Long commentId;
    private Long memberId;
    private CommentLikeType type;

    @Builder
    public CommentLikeServiceRequest(Long commentId, Long memberId, CommentLikeType type) {
        this.commentId = commentId;
        this.memberId = memberId;
        this.type = type;
    }

    public CommentLike toEntity() {
        return CommentLike.builder()
                .type(type)
                .comment(Comment.builder().id(commentId).build())
                .member(Member.builder().id(memberId).build())
                .build();
    }
}
