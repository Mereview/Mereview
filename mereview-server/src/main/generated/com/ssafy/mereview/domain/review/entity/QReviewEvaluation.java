package com.ssafy.mereview.domain.review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewEvaluation is a Querydsl query type for ReviewEvaluation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewEvaluation extends EntityPathBase<ReviewEvaluation> {

    private static final long serialVersionUID = -1456375364L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewEvaluation reviewEvaluation = new QReviewEvaluation("reviewEvaluation");

    public final com.ssafy.mereview.common.domain.QBaseEntity _super = new com.ssafy.mereview.common.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final EnumPath<EvaluationType> evaluationType = createEnum("evaluationType", EvaluationType.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.ssafy.mereview.domain.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final QReview review;

    public QReviewEvaluation(String variable) {
        this(ReviewEvaluation.class, forVariable(variable), INITS);
    }

    public QReviewEvaluation(Path<? extends ReviewEvaluation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewEvaluation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewEvaluation(PathMetadata metadata, PathInits inits) {
        this(ReviewEvaluation.class, metadata, inits);
    }

    public QReviewEvaluation(Class<? extends ReviewEvaluation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.ssafy.mereview.domain.member.entity.QMember(forProperty("member")) : null;
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
    }

}

