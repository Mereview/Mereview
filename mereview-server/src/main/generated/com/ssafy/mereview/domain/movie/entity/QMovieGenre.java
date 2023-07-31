package com.ssafy.mereview.domain.movie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMovieGenre is a Querydsl query type for MovieGenre
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMovieGenre extends EntityPathBase<MovieGenre> {

    private static final long serialVersionUID = 159626381L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMovieGenre movieGenre = new QMovieGenre("movieGenre");

    public final com.ssafy.mereview.domain.QBaseEntity _super = new com.ssafy.mereview.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final QGenre genre;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedTime = _super.modifiedTime;

    public final QMovie movie;

    public QMovieGenre(String variable) {
        this(MovieGenre.class, forVariable(variable), INITS);
    }

    public QMovieGenre(Path<? extends MovieGenre> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMovieGenre(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMovieGenre(PathMetadata metadata, PathInits inits) {
        this(MovieGenre.class, metadata, inits);
    }

    public QMovieGenre(Class<? extends MovieGenre> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.genre = inits.isInitialized("genre") ? new QGenre(forProperty("genre")) : null;
        this.movie = inits.isInitialized("movie") ? new QMovie(forProperty("movie")) : null;
    }

}

