package com.ssafy.mereview.domain.member.entity;

import com.ssafy.mereview.api.service.member.dto.response.ProfileImageResponse;
import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.BaseEntity;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member member;

    @Builder
    private ProfileImage(Long id, UploadFile uploadFile, Member member) {
        this.id = id;
        this.uploadFile = uploadFile;
        this.member = member;
    }

    public void update(UploadFile uploadFile){
        this.uploadFile = uploadFile;
    }

}
