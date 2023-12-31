package com.ssafy.mereview.domain.review.entity;

import com.ssafy.mereview.api.service.review.dto.request.CommentUpdateServiceRequest;
import com.ssafy.mereview.domain.BaseEntity;
import com.ssafy.mereview.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Comment extends BaseEntity {
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "comment", cascade = ALL, orphanRemoval = true)
    private List<CommentLike> likes = new ArrayList<>();

    @Builder
    private Comment(Long id, String content, Review review, Member member, List<CommentLike> likes) {
        this.id = id;
        this.content = content;
        this.review = review;
        this.member = member;
        this.likes = likes;
    }

    public void update(CommentUpdateServiceRequest request) {
        this.content = request.getContent();
    }

}
