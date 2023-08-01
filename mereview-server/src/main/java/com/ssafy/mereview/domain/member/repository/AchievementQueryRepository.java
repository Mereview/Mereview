package com.ssafy.mereview.domain.member.repository;

import com.ssafy.mereview.domain.member.entity.Achievement;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementQueryRepository {
    public List<Achievement> searchAllAchievement();
}
