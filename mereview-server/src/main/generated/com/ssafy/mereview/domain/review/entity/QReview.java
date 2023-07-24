package com.ssafy.mereview.domain.review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = 1412042336L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final com.ssafy.mereview.common.domain.QBaseEntity _super = new com.ssafy.mereview.common.domain.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final EnumPath<EvaluationType> evaluationType = createEnum("evaluationType", EvaluationType.class);

    public final StringPath highlight = createString("highlight");

    public final NumberPath<Integer> hit = createNumber("hit", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<KeywordWeight, QKeywordWeight> keywordWeights = this.<KeywordWeight, QKeywordWeight>createList("keywordWeights", KeywordWeight.class, QKeywordWeight.class, PathInits.DIRECT2);

    public final com.ssafy.mereview.domain.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final com.ssafy.mereview.domain.movie.entity.QMovie movie;

    public final ListPath<Comment, QComment> reviewComments = this.<Comment, QComment>createList("reviewComments", Comment.class, QComment.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.ssafy.mereview.domain.member.entity.QMember(forProperty("member")) : null;
        this.movie = inits.isInitialized("movie") ? new com.ssafy.mereview.domain.movie.entity.QMovie(forProperty("movie")) : null;
    }

}

