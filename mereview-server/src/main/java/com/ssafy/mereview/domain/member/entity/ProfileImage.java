package com.ssafy.mereview.domain.member.entity;

import ch.qos.logback.core.boolex.EvaluationException;
import com.ssafy.mereview.api.service.review.dto.response.BackgroundImageResponse;
import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.BaseEntity;
import com.ssafy.mereview.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ProfileImage extends BaseEntity {

    @Column(name = "profile_image_id")
    @Id @GeneratedValue
    private Long id;

    @Embedded
    UploadFile uploadFile;

    @OneToOne
    @JoinColumn(name = "member_id")
    Member member;

    @Builder
    private ProfileImage(Long id, UploadFile uploadFile, Member member) {
        this.id = id;
        this.uploadFile = uploadFile;
        this.member = member;
    }
}
