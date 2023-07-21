package com.ssafy.mereview.domain.member.repository;

import com.ssafy.mereview.domain.member.entity.MemberAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAchievementRepository extends JpaRepository<MemberAchievement, Long> {
}
