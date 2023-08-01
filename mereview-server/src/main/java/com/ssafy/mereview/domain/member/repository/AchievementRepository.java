package com.ssafy.mereview.domain.member.repository;

import com.ssafy.mereview.domain.member.entity.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long>{
}
