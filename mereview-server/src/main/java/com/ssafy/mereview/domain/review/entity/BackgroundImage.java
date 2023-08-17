package com.ssafy.mereview.domain.review.entity;

import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class BackgroundImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "background_image_id")
    private Long id;
    @Embedded
    UploadFile uploadFile;
    @OneToOne(fetch = LAZY)
    @OnDelete(action = CASCADE)
    @JoinColumn(name = "review_id")
    private Review review;

    @Builder
    private BackgroundImage(Long id, UploadFile uploadFile, Review review) {
        this.id = id;
        this.uploadFile = uploadFile;
        this.review = review;
    }

}
