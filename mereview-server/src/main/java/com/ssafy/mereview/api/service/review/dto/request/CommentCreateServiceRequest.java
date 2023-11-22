package com.ssafy.mereview.api.service.review.dto.request;

import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.review.entity.Comment;
import com.ssafy.mereview.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateServiceRequest {

    private String content;
    private Long reviewId;
    private Long memberId;
    private Long genreId;

    @Builder
    public CommentCreateServiceRequest(String content, Long reviewId, Long memberId, Long genreId) {
        this.content = content;
        this.reviewId = reviewId;
        this.memberId = memberId;
        this.genreId = genreId;
    }

    public Comment toEntity() {
        return Comment.builder()
                .member(Member.builder().id(memberId).build())
                .review(Review.builder().id(reviewId).build())
                .content(content)
                .build();
    }
}
