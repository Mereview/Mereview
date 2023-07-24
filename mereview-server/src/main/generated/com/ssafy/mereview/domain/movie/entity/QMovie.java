package com.ssafy.mereview.domain.movie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMovie is a Querydsl query type for Movie
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMovie extends EntityPathBase<Movie> {

    private static final long serialVersionUID = 332829334L;

    public static final QMovie movie = new QMovie("movie");

    public final com.ssafy.mereview.common.domain.QBaseEntity _super = new com.ssafy.mereview.common.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final ListPath<MovieGenre, QMovieGenre> movieGenres = this.<MovieGenre, QMovieGenre>createList("movieGenres", MovieGenre.class, QMovieGenre.class, PathInits.DIRECT2);

    public final StringPath overview = createString("overview");

    public final NumberPath<Double> popularity = createNumber("popularity", Double.class);

    public final StringPath posterImg = createString("posterImg");

    public final StringPath releaseDate = createString("releaseDate");

    public final ListPath<com.ssafy.mereview.domain.review.entity.Review, com.ssafy.mereview.domain.review.entity.QReview> reviews = this.<com.ssafy.mereview.domain.review.entity.Review, com.ssafy.mereview.domain.review.entity.QReview>createList("reviews", com.ssafy.mereview.domain.review.entity.Review.class, com.ssafy.mereview.domain.review.entity.QReview.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public QMovie(String variable) {
        super(Movie.class, forVariable(variable));
    }

    public QMovie(Path<? extends Movie> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMovie(PathMetadata metadata) {
        super(Movie.class, metadata);
    }

}

