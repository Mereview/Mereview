package com.ssafy.mereview.domain.review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBackgroundImage is a Querydsl query type for BackgroundImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBackgroundImage extends EntityPathBase<BackgroundImage> {

    private static final long serialVersionUID = 587580101L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBackgroundImage backgroundImage = new QBackgroundImage("backgroundImage");

    public final com.ssafy.mereview.domain.QBaseEntity _super = new com.ssafy.mereview.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedTime = _super.modifiedTime;

    public final QReview review;

    public final com.ssafy.mereview.common.util.file.QUploadFile uploadFile;

    public QBackgroundImage(String variable) {
        this(BackgroundImage.class, forVariable(variable), INITS);
    }

    public QBackgroundImage(Path<? extends BackgroundImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBackgroundImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBackgroundImage(PathMetadata metadata, PathInits inits) {
        this(BackgroundImage.class, metadata, inits);
    }

    public QBackgroundImage(Class<? extends BackgroundImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
        this.uploadFile = inits.isInitialized("uploadFile") ? new com.ssafy.mereview.common.util.file.QUploadFile(forProperty("uploadFile")) : null;
    }

}

