package com.ssafy.mereview.domain.review.repository;

import com.ssafy.mereview.domain.review.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
