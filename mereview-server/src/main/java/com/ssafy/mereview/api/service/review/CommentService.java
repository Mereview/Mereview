package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.review.dto.request.CommentCreateServiceRequest;
import com.ssafy.mereview.domain.review.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    public Long save(CommentCreateServiceRequest request) {
        return commentRepository.save(request.toEntity()).getId();
    }
}
