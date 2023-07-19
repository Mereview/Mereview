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
public class Interest extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String interest;

    private String genre_id;

    @Builder
    public Interest(Member member, String interest, String genre_id) {
        this.member = member;
        this.interest = interest;
        this.genre_id = genre_id;
    }

    public void setMember(Member member){
        this.member = member;
    }



}
