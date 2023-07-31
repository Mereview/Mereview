package com.ssafy.mereview.domain.member.repository;

import com.ssafy.mereview.domain.member.entity.Achievement;

import java.util.List;

public interface AchievementQueryRepository {
    public List<Achievement> searchAllAchievement();
}
