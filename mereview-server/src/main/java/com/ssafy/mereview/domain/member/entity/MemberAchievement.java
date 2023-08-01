package com.ssafy.mereview.domain.member.entity;


import com.ssafy.mereview.api.service.member.dto.response.MemberAchievementResponse;
import com.ssafy.mereview.api.service.member.dto.response.MemberResponse;
import com.ssafy.mereview.domain.BaseEntity;
import com.ssafy.mereview.domain.movie.entity.Genre;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class MemberAchievement extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="genre_id")
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="achievement_id")
    private Achievement achievement;



    private String achievementRank;


    @Builder
    public MemberAchievement(Long id, Member member, Genre genre, Achievement achievement, String achievementRank) {
        this.id = id;
        this.member = member;
        this.genre = genre;
        this.achievement = achievement;
        this.achievementRank = achievementRank;
    }

    public MemberAchievementResponse of(){
        return
        MemberAchievementResponse.builder()
                .genreName(genre.getGenreName())
                .achievementName(achievement.getAchievementName())
                .achievementRank(achievementRank)
                .build();

    }
}
