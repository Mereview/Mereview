package com.ssafy.mereview.domain.member.entity;


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
public class MemberTier extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "fun_tier")
    @ColumnDefault(value = "'Bronze'")
    private String funTier;

    @Column(name = "useful_tier")
    @ColumnDefault("'Bronze'")
    private String usefulTier;

    @Column(name = "fun_experience")
    @ColumnDefault("0")
    private int funExperience;

    @Column(name = "useful_experience")
    @ColumnDefault("0")
    private int usefulExperience;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "genre_id")
    private Genre genre;


    @Builder
    public MemberTier(Long id, Member member, String funTier, String usefulTier, int funExperience, int usefulExperience, Genre genre) {
        this.id = id;
        this.member = member;
        this.funTier = funTier;
        this.usefulTier = usefulTier;
        this.funExperience = funExperience;
        this.usefulExperience = usefulExperience;
        this.genre = genre;
    }
}
