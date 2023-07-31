package com.ssafy.mereview.domain.review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
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

    public final com.ssafy.mereview.domain.QBaseEntity _super = new com.ssafy.mereview.domain.QBaseEntity(this);

    public final ListPath<Attachment, QAttachment> attachments = this.<Attachment, QAttachment>createList("attachments", Attachment.class, QAttachment.class, PathInits.DIRECT2);

    public final QBackgroundImage backgroundImage;

    public final ListPath<Comment, QComment> comments = this.<Comment, QComment>createList("comments", Comment.class, QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final StringPath highlight = createString("highlight");

    public final NumberPath<Integer> hits = createNumber("hits", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<ReviewLike, QReviewLike> likes = this.<ReviewLike, QReviewLike>createList("likes", ReviewLike.class, QReviewLike.class, PathInits.DIRECT2);

    public final com.ssafy.mereview.domain.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedTime = _super.modifiedTime;

    public final com.ssafy.mereview.domain.movie.entity.QMovie movie;

    public final StringPath title = createString("title");

    public final EnumPath<EvaluationType> type = createEnum("type", EvaluationType.class);

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
        this.backgroundImage = inits.isInitialized("backgroundImage") ? new QBackgroundImage(forProperty("backgroundImage"), inits.get("backgroundImage")) : null;
        this.member = inits.isInitialized("member") ? new com.ssafy.mereview.domain.member.entity.QMember(forProperty("member")) : null;
        this.movie = inits.isInitialized("movie") ? new com.ssafy.mereview.domain.movie.entity.QMovie(forProperty("movie")) : null;
    }

}

