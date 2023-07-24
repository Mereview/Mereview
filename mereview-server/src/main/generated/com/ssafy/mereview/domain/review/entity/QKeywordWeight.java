package com.ssafy.mereview.domain.review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QKeywordWeight is a Querydsl query type for KeywordWeight
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKeywordWeight extends EntityPathBase<KeywordWeight> {

    private static final long serialVersionUID = 1778641337L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QKeywordWeight keywordWeight = new QKeywordWeight("keywordWeight");

    public final com.ssafy.mereview.common.domain.QBaseEntity _super = new com.ssafy.mereview.common.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QKeyword keyword;

    public final com.ssafy.mereview.domain.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final QReview review;

    public final NumberPath<Integer> weight = createNumber("weight", Integer.class);

    public QKeywordWeight(String variable) {
        this(KeywordWeight.class, forVariable(variable), INITS);
    }

    public QKeywordWeight(Path<? extends KeywordWeight> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QKeywordWeight(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QKeywordWeight(PathMetadata metadata, PathInits inits) {
        this(KeywordWeight.class, metadata, inits);
    }

    public QKeywordWeight(Class<? extends KeywordWeight> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.keyword = inits.isInitialized("keyword") ? new QKeyword(forProperty("keyword")) : null;
        this.member = inits.isInitialized("member") ? new com.ssafy.mereview.domain.member.entity.QMember(forProperty("member")) : null;
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
    }

}

