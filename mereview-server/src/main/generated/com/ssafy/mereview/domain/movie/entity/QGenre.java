package com.ssafy.mereview.domain.movie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGenre is a Querydsl query type for Genre
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGenre extends EntityPathBase<Genre> {

    private static final long serialVersionUID = 326982889L;

    public static final QGenre genre = new QGenre("genre");

    public final com.ssafy.mereview.domain.QBaseEntity _super = new com.ssafy.mereview.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final StringPath genreName = createString("genreName");

    public final NumberPath<Integer> genreNumber = createNumber("genreNumber", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.ssafy.mereview.domain.member.entity.Interest, com.ssafy.mereview.domain.member.entity.QInterest> interests = this.<com.ssafy.mereview.domain.member.entity.Interest, com.ssafy.mereview.domain.member.entity.QInterest>createList("interests", com.ssafy.mereview.domain.member.entity.Interest.class, com.ssafy.mereview.domain.member.entity.QInterest.class, PathInits.DIRECT2);

    public final BooleanPath isUsing = createBoolean("isUsing");

    public final ListPath<com.ssafy.mereview.domain.member.entity.MemberAchievement, com.ssafy.mereview.domain.member.entity.QMemberAchievement> memberAchievements = this.<com.ssafy.mereview.domain.member.entity.MemberAchievement, com.ssafy.mereview.domain.member.entity.QMemberAchievement>createList("memberAchievements", com.ssafy.mereview.domain.member.entity.MemberAchievement.class, com.ssafy.mereview.domain.member.entity.QMemberAchievement.class, PathInits.DIRECT2);

    public final ListPath<com.ssafy.mereview.domain.member.entity.MemberTier, com.ssafy.mereview.domain.member.entity.QMemberTier> memberTiers = this.<com.ssafy.mereview.domain.member.entity.MemberTier, com.ssafy.mereview.domain.member.entity.QMemberTier>createList("memberTiers", com.ssafy.mereview.domain.member.entity.MemberTier.class, com.ssafy.mereview.domain.member.entity.QMemberTier.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedTime = _super.modifiedTime;

    public final ListPath<MovieGenre, QMovieGenre> movieGenres = this.<MovieGenre, QMovieGenre>createList("movieGenres", MovieGenre.class, QMovieGenre.class, PathInits.DIRECT2);

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

