package com.ssafy.mereview.domain.movie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGenre is a Querydsl query type for Genre
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGenre extends EntityPathBase<Genre> {

    private static final long serialVersionUID = 326982889L;

    public static final QGenre genre = new QGenre("genre");

    public final NumberPath<Long> genreId = createNumber("genreId", Long.class);

    public final StringPath genreName = createString("genreName");

    public final ListPath<com.ssafy.mereview.domain.member.entity.Interest, com.ssafy.mereview.domain.member.entity.QInterest> interests = this.<com.ssafy.mereview.domain.member.entity.Interest, com.ssafy.mereview.domain.member.entity.QInterest>createList("interests", com.ssafy.mereview.domain.member.entity.Interest.class, com.ssafy.mereview.domain.member.entity.QInterest.class, PathInits.DIRECT2);

    public final BooleanPath isUsing = createBoolean("isUsing");

    public final ListPath<com.ssafy.mereview.domain.member.entity.MemberAchievement, com.ssafy.mereview.domain.member.entity.QMemberAchievement> memberAchievements = this.<com.ssafy.mereview.domain.member.entity.MemberAchievement, com.ssafy.mereview.domain.member.entity.QMemberAchievement>createList("memberAchievements", com.ssafy.mereview.domain.member.entity.MemberAchievement.class, com.ssafy.mereview.domain.member.entity.QMemberAchievement.class, PathInits.DIRECT2);

    public final ListPath<com.ssafy.mereview.domain.member.entity.UserTier, com.ssafy.mereview.domain.member.entity.QUserTier> userTiers = this.<com.ssafy.mereview.domain.member.entity.UserTier, com.ssafy.mereview.domain.member.entity.QUserTier>createList("userTiers", com.ssafy.mereview.domain.member.entity.UserTier.class, com.ssafy.mereview.domain.member.entity.QUserTier.class, PathInits.DIRECT2);

    public QGenre(String variable) {
        super(Genre.class, forVariable(variable));
    }

    public QGenre(Path<? extends Genre> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGenre(PathMetadata metadata) {
        super(Genre.class, metadata);
    }

}

