package com.ssafy.mereview.domain.review.repository.command;

import com.ssafy.mereview.domain.review.entity.BackgroundImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackgroundImageRepository extends JpaRepository<BackgroundImage, Long> {
}
