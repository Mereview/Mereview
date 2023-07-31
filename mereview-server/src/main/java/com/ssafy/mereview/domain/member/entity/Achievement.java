package com.ssafy.mereview.domain.member.entity;

import com.ssafy.mereview.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Achievement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String achievementName;

    @OneToMany(mappedBy = "achievement")
    private List<MemberAchievement> memberAchievement = new ArrayList<>();

    @Builder
    public Achievement(Long id, String achievementName, List<MemberAchievement> memberAchievement) {
        this.id = id;
        this.achievementName = achievementName;
        this.memberAchievement = memberAchievement;
    }
}
