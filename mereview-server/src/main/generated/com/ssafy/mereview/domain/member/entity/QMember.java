package com.ssafy.mereview.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -429565596L;

    public static final QMember member = new QMember("member1");

    public final com.ssafy.mereview.domain.QBaseEntity _super = new com.ssafy.mereview.domain.QBaseEntity(this);

    public final StringPath birthDate = createString("birthDate");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final StringPath email = createString("email");

    public final StringPath gender = createString("gender");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Interest, QInterest> interests = this.<Interest, QInterest>createList("interests", Interest.class, QInterest.class, PathInits.DIRECT2);

    public final ListPath<MemberTier, QMemberTier> memberTiers = this.<MemberTier, QMemberTier>createList("memberTiers", MemberTier.class, QMemberTier.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedTime = _super.modifiedTime;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final ListPath<com.ssafy.mereview.domain.review.entity.Review, com.ssafy.mereview.domain.review.entity.QReview> reviews = this.<com.ssafy.mereview.domain.review.entity.Review, com.ssafy.mereview.domain.review.entity.QReview>createList("reviews", com.ssafy.mereview.domain.review.entity.Review.class, com.ssafy.mereview.domain.review.entity.QReview.class, PathInits.DIRECT2);

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

