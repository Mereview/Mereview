package com.ssafy.mereview.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAchievement is a Querydsl query type for Achievement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAchievement extends EntityPathBase<Achievement> {

    private static final long serialVersionUID = -171217243L;

    public static final QAchievement achievement = new QAchievement("achievement");

    public final com.ssafy.mereview.common.domain.QBaseEntity _super = new com.ssafy.mereview.common.domain.QBaseEntity(this);

    public final StringPath achievementName = createString("achievementName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<MemberAchievement, QMemberAchievement> memberAchievement = this.<MemberAchievement, QMemberAchievement>createList("memberAchievement", MemberAchievement.class, QMemberAchievement.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public QAchievement(String variable) {
        super(Achievement.class, forVariable(variable));
    }

    public QAchievement(Path<? extends Achievement> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAchievement(PathMetadata metadata) {
        super(Achievement.class, metadata);
    }

}

