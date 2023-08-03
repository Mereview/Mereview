package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.controller.review.dto.request.CommentUpdateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.request.CommentCreateServiceRequest;
import com.ssafy.mereview.domain.review.entity.Comment;
import com.ssafy.mereview.domain.review.repository.CommentRepository;
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

    public Long update(Long commentId, CommentUpdateServiceRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(NoSuchElementException::new);
        comment.update(request);
        Long updateId = comment.getId();
        return updateId;
    }

    public Long delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(NoSuchElementException::new);
        Long deleteId = comment.getId();
        commentRepository.delete(comment);
        return deleteId;
    }
}
