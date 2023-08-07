package com.ssafy.mereview.domain.review.entity;

import com.ssafy.mereview.domain.BaseEntity;
import com.ssafy.mereview.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;

import static com.ssafy.mereview.domain.review.entity.NotificationStatus.CONFIRMED;
import static com.ssafy.mereview.domain.review.entity.NotificationStatus.UNCONFIRMED;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;

    @Builder
    public Notification(Long id, Member member, Review review) {
        this.id = id;
        this.member = member;
        this.review = review;
        status = UNCONFIRMED;
    }

    public void toggleStatus() {
        if (status.equals(CONFIRMED)) {
            status = UNCONFIRMED;
        } else {
            status = CONFIRMED;
        }
    }
}
