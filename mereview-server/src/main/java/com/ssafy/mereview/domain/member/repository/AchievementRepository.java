package com.ssafy.mereview.domain.member.repository;

import com.ssafy.mereview.domain.member.entity.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementRepository extends JpaRepository<Achievement, Long>{
}
