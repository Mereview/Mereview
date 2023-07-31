package com.ssafy.mereview.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberAchievement is a Querydsl query type for MemberAchievement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberAchievement extends EntityPathBase<MemberAchievement> {

    private static final long serialVersionUID = 670160683L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberAchievement memberAchievement = new QMemberAchievement("memberAchievement");

    public final com.ssafy.mereview.domain.QBaseEntity _super = new com.ssafy.mereview.domain.QBaseEntity(this);

    public final QAchievement achievement;

    public final StringPath achievementRank = createString("achievementRank");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final com.ssafy.mereview.domain.movie.entity.QGenre genre;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedTime = _super.modifiedTime;

    public QMemberAchievement(String variable) {
        this(MemberAchievement.class, forVariable(variable), INITS);
    }

    public QMemberAchievement(Path<? extends MemberAchievement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberAchievement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberAchievement(PathMetadata metadata, PathInits inits) {
        this(MemberAchievement.class, metadata, inits);
    }

    public QMemberAchievement(Class<? extends MemberAchievement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.achievement = inits.isInitialized("achievement") ? new QAchievement(forProperty("achievement")) : null;
        this.genre = inits.isInitialized("genre") ? new com.ssafy.mereview.domain.movie.entity.QGenre(forProperty("genre")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

