package com.ssafy.mereview.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.mereview.domain.member.entity.Achievement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.mereview.domain.member.entity.QAchievement.achievement;


@Repository
@RequiredArgsConstructor
public class AchievementQueryRepositoryImpl implements AchievementQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Achievement> searchAllAchievement() {
        List<Achievement> achievementList = queryFactory.select(achievement)
                .from(achievement)
                .fetch();

        return achievementList;
    }
}
