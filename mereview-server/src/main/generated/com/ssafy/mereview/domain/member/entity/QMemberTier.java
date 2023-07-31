package com.ssafy.mereview.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberTier is a Querydsl query type for MemberTier
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberTier extends EntityPathBase<MemberTier> {

    private static final long serialVersionUID = 398052710L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberTier memberTier = new QMemberTier("memberTier");

    public final com.ssafy.mereview.domain.QBaseEntity _super = new com.ssafy.mereview.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final NumberPath<Integer> funExperience = createNumber("funExperience", Integer.class);

    public final StringPath funTier = createString("funTier");

    public final com.ssafy.mereview.domain.movie.entity.QGenre genre;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedTime = _super.modifiedTime;

    public final NumberPath<Integer> usefulExperience = createNumber("usefulExperience", Integer.class);

    public final StringPath usefulTier = createString("usefulTier");

    public QMemberTier(String variable) {
        this(MemberTier.class, forVariable(variable), INITS);
    }

    public QMemberTier(Path<? extends MemberTier> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberTier(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberTier(PathMetadata metadata, PathInits inits) {
        this(MemberTier.class, metadata, inits);
    }

    public QMemberTier(Class<? extends MemberTier> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.genre = inits.isInitialized("genre") ? new com.ssafy.mereview.domain.movie.entity.QGenre(forProperty("genre")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

