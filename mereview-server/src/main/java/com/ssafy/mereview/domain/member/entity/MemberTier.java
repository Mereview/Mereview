package com.ssafy.mereview.domain.member.entity;


import com.ssafy.mereview.domain.BaseEntity;
import com.ssafy.mereview.domain.movie.entity.Genre;
import com.ssafy.mereview.domain.review.entity.ReviewEvaluationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

import static com.ssafy.mereview.common.util.ExperienceConstants.TIER_MAX_EXP_MAP;
import static com.ssafy.mereview.domain.member.entity.Rank.*;
import static com.ssafy.mereview.domain.review.entity.ReviewEvaluationType.FUN;
import static com.ssafy.mereview.domain.review.entity.ReviewEvaluationType.USEFUL;

@ToString
@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
public class MemberTier extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "fun_tier")
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NONE'")
    private Rank funTier;

    @Column(name = "useful_tier")
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NONE'")
    private Rank usefulTier;

    @Column(name = "fun_experience")
    private int funExperience;

    @Column(name = "useful_experience")
    private int usefulExperience;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Builder
    public MemberTier(Long id, Member member, Rank funTier, Rank usefulTier, int funExperience, int usefulExperience, Genre genre) {
        this.id = id;
        this.member = member;
        this.funTier = funTier;
        this.usefulTier = usefulTier;
        this.funExperience = funExperience;
        this.usefulExperience = usefulExperience;
        this.genre = genre;
    }

    /** business logics **/

    public void increaseExp(ReviewEvaluationType type) {
        if (type.equals(FUN)) {
            funExperience += 5;
            checkAndPromoteFunTier();
        } else if (type.equals(USEFUL)) {
            usefulExperience += 5;
            checkAndPromoteUsefulTier();
        }

    }

    public void decreaseExp() {
        funExperience -= 5;
        usefulExperience -= 5;
        checkExpAndDemote();
    }

    /** private methods **/

    private void checkAndPromoteFunTier() {
        if (checkFunTierPromotionEligibility()) {
            funTier = promote(funTier);
            funExperience = 0;
        }
    }

    private void checkAndPromoteUsefulTier() {
        if (checkUsefulTierPromotionEligibility()) {
            usefulTier = promote(usefulTier);
            usefulExperience = 0;
        }
    }

    private void checkExpAndDemote() {
        if (checkDemotionEligibility(funExperience)) {
            funTier = demote(funTier);
            funExperience = 0;
        } else if (checkDemotionEligibility(usefulExperience)) {
            usefulTier = demote(usefulTier);
            usefulExperience = 0;
        }
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

    private Rank demote(Rank tier) {
        switch (tier) {
            case DIAMOND:
                return PLATINUM;
            case PLATINUM:
                return GOLD;
            case GOLD:
                return SILVER;
            case SILVER:
                return BRONZE;
            default:
                return NONE;
        }
    }

    private boolean checkFunTierPromotionEligibility() {
            return funExperience >= TIER_MAX_EXP_MAP.get(funTier);
    }

    private boolean checkUsefulTierPromotionEligibility() {
        return usefulExperience >= TIER_MAX_EXP_MAP.get(usefulTier);
    }

    private boolean checkDemotionEligibility(int experience) {
        return experience <= 0;
    }

}

