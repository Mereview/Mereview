package com.ssafy.mereview.domain.member.entity;

import com.ssafy.mereview.common.domain.BaseEntity;
import com.ssafy.mereview.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String password;

    private String nickname;

    private String gender;

    @Column(name = "birth_date")
    private String birthDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Interest> interests = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserTier> userTiers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();




    @Builder
    public Member(Long id, String email, String password, String nickname, String gender, String birthDate, Role role, List<Interest> interests, List<UserTier> userTiers, List<Review> reviews) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.role = role;
        this.interests = interests;
        this.userTiers = userTiers;
        this.reviews = reviews;
    }
}
