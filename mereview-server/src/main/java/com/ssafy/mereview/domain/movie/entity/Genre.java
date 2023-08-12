package com.ssafy.mereview.domain.movie.entity;


import com.ssafy.mereview.domain.BaseEntity;
import com.ssafy.mereview.domain.member.entity.Interest;
import com.ssafy.mereview.domain.member.entity.MemberAchievement;
import com.ssafy.mereview.domain.member.entity.MemberTier;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@DynamicInsert
public class Genre extends BaseEntity {

    @Id
    @Column(name = "genre_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private int genreNumber;

    @Column(nullable = false, unique = true)
    private String genreName;

//    @Column(nullable = false, unique = true)
//    private GenreName genreName;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean isUsing;

    @OneToMany(mappedBy = "genre")
    private List<MovieGenre> movieGenres = new ArrayList<>();

    @OneToMany(mappedBy = "genre")
    private List<MemberTier> memberTiers = new ArrayList<>();

    @OneToMany(mappedBy = "genre")
    private List<Interest> interests = new ArrayList<>();

    @OneToMany(mappedBy = "genre")
    private List<MemberAchievement> memberAchievements = new ArrayList<>();

    @Builder
    public Genre(Long id, int genreNumber, String genreName, boolean isUsing, List<MemberTier> memberTiers, List<Interest> interests, List<MemberAchievement> memberAchievements) {
        this.id = id;
        this.genreNumber = genreNumber;
        this.genreName = genreName;
        this.isUsing = true;
        this.memberTiers = memberTiers;
        this.interests = interests;
        this.memberAchievements = memberAchievements;
    }

}
