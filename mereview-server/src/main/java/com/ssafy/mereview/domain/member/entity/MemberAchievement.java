package com.ssafy.mereview.domain.member.entity;

import com.ssafy.mereview.common.domain.BaseEntity;
import com.ssafy.mereview.domain.movie.entity.Genre;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sun.security.x509.GeneralName;

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
    public MemberAchievement(Member member, Genre genre, Achievement achievement, String achievementRank) {
        this.member = member;
        this.genre = genre;
        this.achievement = achievement;
        this.achievementRank = achievementRank;
    }






}
