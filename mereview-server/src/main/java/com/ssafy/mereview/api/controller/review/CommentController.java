package com.ssafy.mereview.api.controller.review;

import com.ssafy.mereview.api.controller.review.dto.request.CommentCreateRequest;
import com.ssafy.mereview.api.controller.review.dto.request.CommentLikeRequest;
import com.ssafy.mereview.api.controller.review.dto.request.CommentUpdateRequest;
import com.ssafy.mereview.api.service.review.CommentLikeService;
import com.ssafy.mereview.api.service.review.CommentService;
import com.ssafy.mereview.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/reviews/comments")
@RestController
public class CommentController {

    private final CommentService commentService;
    private final CommentLikeService commentLikeService;

    @PostMapping("/comments")
    public ApiResponse<Long> createReviewComment(@Valid @RequestBody CommentCreateRequest request) {
        Long saveId = commentService.save(request.toServiceRequest());
        return ApiResponse.ok(saveId);
    }

    @PutMapping("/comments/{commentId}")
    public ApiResponse<Long> updateReviewComment(@PathVariable Long commentId,
                                                 @Valid @RequestBody CommentUpdateRequest request) {
        Long updateId = commentService.update(commentId, request.toServiceRequest());
        return ApiResponse.ok(updateId);
    }

    @DeleteMapping("/comments/{commentId}")
    public ApiResponse<Long> deleteReviewComment(@PathVariable Long commentId) {
        Long deleteId = commentService.delete(commentId);
        return ApiResponse.ok(deleteId);
    }

    @PostMapping("/comments/likes")
    public ApiResponse<Long> createCommentLike(@Valid @RequestBody CommentLikeRequest request) {
        Long saveId = commentLikeService.create(request.toServiceRequest());
        return ApiResponse.ok(saveId);
    }

    @DeleteMapping("/comments/likes/{likeId}")
    public ApiResponse<Long> deleteCommentLike(@PathVariable Long likeId) {
        Long deleteId = commentLikeService.delete(likeId);
        return ApiResponse.ok(deleteId);
    }
}
