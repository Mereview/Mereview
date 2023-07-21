package com.ssafy.mereview.domain.member.entity;

import com.ssafy.mereview.common.domain.BaseEntity;
import com.ssafy.mereview.domain.movie.entity.Genre;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class UserTier extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "fun_tier")
    @ColumnDefault("'Bronze'")
    private String funTier;

    @Column(name = "useful_tier")
    @ColumnDefault("'Bronze'")
    private String usefulTier;

    @Column(name = "fun_experience")
    private int funExperience;

    @Column(name = "useful_experience")
    private int usefulExperience;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;


    @Builder
    public UserTier(Long id, Member member, String funTier, String usefulTier, int funExperience, int usefulExperience, Genre genre) {
        this.id = id;
        this.member = member;
        this.funTier = funTier;
        this.usefulTier = usefulTier;
        this.funExperience = funExperience;
        this.usefulExperience = usefulExperience;
        this.genre = genre;
    }
}
