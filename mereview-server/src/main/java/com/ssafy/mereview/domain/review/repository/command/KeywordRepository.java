package com.ssafy.mereview.domain.review.repository.command;

import com.ssafy.mereview.domain.review.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}
