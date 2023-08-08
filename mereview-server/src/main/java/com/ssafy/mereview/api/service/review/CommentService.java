package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.review.dto.request.CommentUpdateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.request.CommentCreateServiceRequest;
import com.ssafy.mereview.domain.review.entity.Comment;
import com.ssafy.mereview.domain.review.repository.command.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public Long save(CommentCreateServiceRequest request) {
        return commentRepository.save(request.toEntity()).getId();
    }

    public Long update(CommentUpdateServiceRequest request) {
        Comment comment = commentRepository.findById(request.getCommentId())
                .orElseThrow(NoSuchElementException::new);
        comment.update(request);
        return request.getCommentId();
    }

    public Long delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(NoSuchElementException::new);
        commentRepository.delete(comment);
        return commentId;
    }
}
