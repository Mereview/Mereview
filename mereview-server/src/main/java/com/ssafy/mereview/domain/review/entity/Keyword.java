package com.ssafy.mereview.domain.review.entity;

import com.ssafy.mereview.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword extends BaseEntity {
    @Id
    @Column(name = "keyword_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "keyword_name", nullable = false)
    private String keywordName;

    @Builder
    public Keyword(Long id, String keywordName) {
        this.id = id;
        this.keywordName = keywordName;
    }
}
