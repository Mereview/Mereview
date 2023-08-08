package com.ssafy.mereview.domain.member.entity;

import com.ssafy.mereview.api.service.member.dto.response.MemberResponse;
import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.BaseEntity;
import com.ssafy.mereview.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String password;

    @Column(unique = true)
    private String nickname;

    private String gender;

    @Column(name = "birth_date")
    private String birthDate;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'USER'")
    private Role role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Interest> interests = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberTier> memberTiers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();

    @OneToOne(mappedBy = "member")
    private ProfileImage profileImage;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private MemberVisit memberVisit;

    private String introduce;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberFollow> followers = new ArrayList<>();

    @OneToMany(mappedBy = "targetMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberFollow> following = new ArrayList<>();

    @Builder
    public Member(Long id, String email, String password, String nickname, String gender, String birthDate, Role role, List<Interest> interests, List<MemberTier> memberTiers, List<Review> reviews, ProfileImage profileImage, MemberVisit memberVisit, String introduce, List<MemberFollow> followers, List<MemberFollow> following) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.role = role;
        this.interests = interests;
        this.memberTiers = memberTiers;
        this.reviews = reviews;
        this.profileImage = profileImage;
        this.memberVisit = memberVisit;
        this.introduce = introduce;
        this.followers = followers;
        this.following = following;
    }

    public MemberResponse of() {
        return
        MemberResponse.builder()
                .id(id)
                .nickname(nickname)
                .email(email)
                .gender(gender)
                .birthDate(birthDate)
                .build();
    }

    //update member
    public void updateNickname(String nickname){
        this.nickname = nickname;
    }

    //update profile image
    public void updateProfileImage(UploadFile uploadFile){
        if(this.profileImage == null){
            this.profileImage = ProfileImage.builder()
                    .uploadFile(uploadFile)
                    .member(this)
                    .build();
        }else{
            this.profileImage.update(uploadFile);
        }
    }

    public void delete(){
        this.role = Role.DELETED;
    }

    public void update(List<Interest> interests) {
        this.interests = interests;
    }

    public void updateIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
