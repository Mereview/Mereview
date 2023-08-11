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

import static com.ssafy.mereview.common.util.SizeConstants.COMMENT_ACHIEVEMENT_MAX_COUNT_MAP;
import static com.ssafy.mereview.common.util.SizeConstants.REVIEW_ACHIEVEMENT_MAX_COUNT_MAP;
import static com.ssafy.mereview.domain.member.entity.AchievementType.COMMENT;
import static com.ssafy.mereview.domain.member.entity.AchievementType.REVIEW;
import static com.ssafy.mereview.domain.member.entity.Rank.*;

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

    public void checkAndPromoteAchievement(int achievementCount) {
        this.achievementCount = achievementCount;
        if (achievementType.equals(REVIEW) && checkReviewAchievementEligibility() ||
                achievementType.equals(COMMENT) && checkCommentAchievementEligibility()) {
            achievementRank = promote(achievementRank);
        }
    }

    private boolean checkReviewAchievementEligibility() {
        return achievementCount >= REVIEW_ACHIEVEMENT_MAX_COUNT_MAP.get(achievementRank);
    }

    private boolean checkCommentAchievementEligibility() {
        return achievementCount >= COMMENT_ACHIEVEMENT_MAX_COUNT_MAP.get(achievementRank);
    }

    private Rank promote(Rank tier) {
        switch (tier) {
            case NONE:
                return BRONZE;
            case BRONZE:
                return SILVER;
            case SILVER:
                return GOLD;
            case GOLD:
                return PLATINUM;
            case PLATINUM:
                return DIAMOND;
            default:
                return tier;
        }
    }

}
