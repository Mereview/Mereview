package com.ssafy.mereview.domain.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class MemberFollow {

    @Id @GeneratedValue
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_member_id")
    Member targetMember;

    @Builder
    public MemberFollow(Long id, Member member, Member targetMember) {
        this.id = id;
        this.member = member;
        this.targetMember = targetMember;
    }
}
