package com.ssafy.mereview.api.controller.review;

import com.ssafy.mereview.api.controller.review.dto.request.CommentCreateRequest;
import com.ssafy.mereview.api.controller.review.dto.request.CommentLikeRequest;
import com.ssafy.mereview.api.controller.review.dto.request.CommentUpdateRequest;
import com.ssafy.mereview.api.service.review.CommentLikeService;
import com.ssafy.mereview.api.service.review.CommentService;
import com.ssafy.mereview.api.service.review.dto.response.CommentLikeResponse;
import com.ssafy.mereview.common.response.ApiResponse;
import com.ssafy.mereview.domain.member.repository.MemberAchievementQueryRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/reviews/comments")
@RestController
@Api(tags = {"댓글 관련 API"})
public class CommentController {

    private final CommentService commentService;
    private final CommentLikeService commentLikeService;

    @PostMapping
    @ApiOperation(value = "리뷰 댓글 생성")
    public ApiResponse<Long> createReviewComment(@Valid @RequestBody CommentCreateRequest request) {
        Long saveId = commentService.save(request.toServiceRequest());
        return ApiResponse.ok(saveId);
    }

    @PutMapping("/{commentId}")
    @ApiOperation(value = "리뷰 댓글 수정")
    public ApiResponse<Long> updateReviewComment(@PathVariable Long commentId,
                                                 @Valid @RequestBody CommentUpdateRequest request) {
        Long updateId = commentService.update(request.toServiceRequest(commentId));
        return ApiResponse.ok(updateId);
    }

    @DeleteMapping("/{commentId}")
    @ApiOperation(value = "리뷰 댓글 삭제")
    public ApiResponse<Long> deleteReviewComment(@PathVariable Long commentId) {
        Long deleteId = commentService.delete(commentId);
        return ApiResponse.ok(deleteId);
    }

    @PostMapping("/likes")
    @ApiOperation(value = "리뷰 댓글 좋아요 생성 및 삭제")
    public ApiResponse<CommentLikeResponse> updateCommentLike(@Valid @RequestBody CommentLikeRequest request) {
        CommentLikeResponse response = commentLikeService.updateCommentLike(request.toServiceRequest());
        return ApiResponse.ok(response);
    }

}
