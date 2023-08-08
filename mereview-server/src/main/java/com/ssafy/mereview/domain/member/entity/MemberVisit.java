package com.ssafy.mereview.domain.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class MemberVisit {

    @Id @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "member_id")
    private Member member;

    @ColumnDefault("0")
    private int todayVisitCount;

    @ColumnDefault("0")
    private int totalVisitCount;

    private LocalDateTime lastVisitDate;

    @Builder
    public MemberVisit(Long id, Member member, int todayVisitCount, int totalVisitCount) {
        this.id = id;
        this.member = member;
        this.todayVisitCount = todayVisitCount;
        this.totalVisitCount = totalVisitCount;
        this.lastVisitDate = LocalDateTime.now();
    }

    public void updateVisitCount() {
        this.todayVisitCount++;
        this.totalVisitCount++;
        if(lastVisitDate.getDayOfMonth() != LocalDateTime.now().getDayOfMonth()) {
            this.todayVisitCount = 1;
        }
        this.lastVisitDate = LocalDateTime.now();
    }
}
