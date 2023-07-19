package com.ssafy.mereview.domain.member.entity;

import com.ssafy.mereview.common.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String funTier;

    @Column(name = "useful_tier")
    private String usefulTier;

    @Column(name = "fun_experience")
    private int funExperience;

    @Column(name = "useful_experience")
    private int usefulExperience;

    @Column(name = "genre_id")
    private String genreId;

    @Builder
    public UserTier(Member member, String funTier, String usefulTier, int funExperience, int usefulExperience, String genreId) {
        this.member = member;
        this.funTier = funTier;
        this.usefulTier = usefulTier;
        this.funExperience = funExperience;
        this.usefulExperience = usefulExperience;
        this.genreId = genreId;
    }
    public void upFunExperience(){
        this.funExperience+=5;
        if
    }

}
