package com.ssafy.mereview.domain.member.entity;

import com.ssafy.mereview.common.domain.BaseEntity;
import com.ssafy.mereview.domain.movie.entity.Genre;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;
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
    public MemberAchievement(Long id, Member member, Genre genre, Achievement achievement, String achievementRank) {
        this.id = id;
        this.member = member;
        this.genre = genre;
        this.achievement = achievement;
        this.achievementRank = achievementRank;
    }
}
