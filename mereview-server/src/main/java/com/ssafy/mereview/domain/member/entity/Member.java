package com.ssafy.mereview.domain.member.entity;

import com.ssafy.mereview.common.domain.BaseEntity;
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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

//    @OneToMany(mappedBy = "review")
//    private List<Review> reviews = new ArrayList<>();

    @Builder
    public Member(String email, String password, String nickname, List<Interest> interests){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = Role.USER;

    }

    public void addInterest(Interest interest){
        this.interests.add(interest);
        interest.setMember(this);
    }

}
