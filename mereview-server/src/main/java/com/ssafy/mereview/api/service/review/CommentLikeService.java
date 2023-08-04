package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.review.dto.request.CommentLikeServiceRequest;
import com.ssafy.mereview.domain.review.entity.CommentLike;
import com.ssafy.mereview.domain.review.repository.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    public Long create(CommentLikeServiceRequest request) {
        return commentLikeRepository.save(request.toEntity()).getId();
    }

    public Long delete(Long commentLikeId) {
        CommentLike commentLike = commentLikeRepository.findById(commentLikeId)
                .orElseThrow(NoSuchElementException::new);
        commentLikeRepository.delete(commentLike);
        return commentLikeId;
    }
}
