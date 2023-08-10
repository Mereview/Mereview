package com.ssafy.mereview.domain.member.entity;

import com.ssafy.mereview.api.service.member.dto.response.MemberAchievementResponse;
import com.ssafy.mereview.domain.BaseEntity;
import com.ssafy.mereview.domain.movie.entity.Genre;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
public class MemberAchievement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "achievement_id")
    private Achievement achievement;


    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NONE'")
    private Rank achievementRank;

    private int achievementPercent;

    private int achievementCount;

    @Enumerated(EnumType.STRING)
    AchievementType achievementType;

    @Builder
    public MemberAchievement(Long id, Member member, Genre genre, Achievement achievement, Rank achievementRank, int achievementPercent, int achievementCount, AchievementType achievementType) {
        this.id = id;
        this.member = member;
        this.genre = genre;
        this.achievement = achievement;
        this.achievementRank = achievementRank;
        this.achievementPercent = achievementPercent;
        this.achievementCount = achievementCount;
        this.achievementType = achievementType;
    }
}
