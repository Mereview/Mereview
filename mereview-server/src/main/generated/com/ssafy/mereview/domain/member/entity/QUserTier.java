package com.ssafy.mereview.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserTier is a Querydsl query type for UserTier
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserTier extends EntityPathBase<UserTier> {

    private static final long serialVersionUID = -112715625L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserTier userTier = new QUserTier("userTier");

    public final com.ssafy.mereview.common.domain.QBaseEntity _super = new com.ssafy.mereview.common.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Integer> funExperience = createNumber("funExperience", Integer.class);

    public final StringPath funTier = createString("funTier");

    public final com.ssafy.mereview.domain.movie.entity.QGenre genre;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final NumberPath<Integer> usefulExperience = createNumber("usefulExperience", Integer.class);

    public final StringPath usefulTier = createString("usefulTier");

    public QUserTier(String variable) {
        this(UserTier.class, forVariable(variable), INITS);
    }

    public QUserTier(Path<? extends UserTier> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserTier(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserTier(PathMetadata metadata, PathInits inits) {
        this(UserTier.class, metadata, inits);
    }

    public QUserTier(Class<? extends UserTier> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.genre = inits.isInitialized("genre") ? new com.ssafy.mereview.domain.movie.entity.QGenre(forProperty("genre")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

