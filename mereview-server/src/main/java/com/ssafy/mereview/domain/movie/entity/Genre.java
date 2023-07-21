package com.ssafy.mereview.domain.movie.entity;

import com.ssafy.mereview.domain.member.entity.Interest;
import com.ssafy.mereview.domain.member.entity.MemberAchievement;
import com.ssafy.mereview.domain.member.entity.UserTier;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Genre {
    @Id
    @Column(name = "genre_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genreId;

    @Column(nullable = false, unique = true)
    private String genreName;

    @OneToMany(mappedBy = "genre")
    private List<UserTier> userTiers = new ArrayList<>();

    @OneToMany(mappedBy = "genre")
    private List<Interest> interests = new ArrayList<>();

    @OneToMany(mappedBy = "genre")
    private List<MemberAchievement> memberAchievements = new ArrayList<>();

    @Column(nullable = false)
    private boolean isUsing;

    @Builder
    public Genre(Long genreId, String genreName, List<UserTier> userTiers, List<Interest> interests, List<MemberAchievement> memberAchievements, boolean isUsing) {
        this.genreId = genreId;
        this.genreName = genreName;
        this.userTiers = userTiers;
        this.interests = interests;
        this.memberAchievements = memberAchievements;
        this.isUsing = isUsing;
    }
}
