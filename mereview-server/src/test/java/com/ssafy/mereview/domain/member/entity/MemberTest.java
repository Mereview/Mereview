package com.ssafy.mereview.domain.member.entity;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.mereview.domain.movie.entity.Genre;
import com.ssafy.mereview.domain.movie.entity.QGenre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(false)
public class MemberTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JPAQueryFactory queryFactory;

    @Test
    public void MemberAddInterestTest() {
        Member member = new Member("duljji@naver.com", "1234", "duljji", null);
        Genre comedy = Genre.builder().genreId("1").genreName("코미디").build();
        Genre melo = Genre.builder().genreId("2").genreName("멜로").build();
        entityManager.persist(comedy);
        entityManager.persist(melo);

        Interest interest1 = new Interest(member, comedy);
        Interest interest2 = new Interest(member, melo);
        entityManager.persist(interest1);
        entityManager.persist(interest2);
        member.addInterest(interest1);
        member.addInterest(interest2);
        entityManager.persist(member);
        entityManager.flush();
        entityManager.clear();
        entityManager.createQuery("select m from Member m where email = :email", Member.class)
                .setParameter("email", "duljji@naver.com")
                .getResultList();

        QMember q = QMember.member;
        QInterest i = QInterest.interest;
        List<Tuple> tuples = queryFactory.select(q, i).from(q).rightJoin(q.interests, i).fetchJoin().fetch();


    }

    @Test
    public void MemberAndTierTest() {

        Member member = new Member("duljji@naver.com", "1234", "duljji", null);
        Genre genre = Genre.builder().genreId("1").genreName("코미디").build();
        UserTier userTier = UserTier.builder().
                member(member)
                .funTier("0")
                .usefulTier("0")
                .funExperience(0)
                .usefulExperience(0)
                .genre(genre)
                .build();
        entityManager.persist(genre);
        entityManager.persist(member);
        entityManager.persist(userTier);
        entityManager.flush();

//        QUserTier q = QUserTier.userTier;
//        QMember qm = QMember.member;
//        List<Tuple> tuples = queryFactory
//                .select(q, qm)
//                .from(q)
//                .join(q.member, qm)
//                .on(q.member.eq(qm))
//                .where(q.genre.eq(genre)).fetch();
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    public void MemberAchievementTest() {

        Member member = Member.builder()
                .email("duljji@naver.com")
                .password("1234")
                .build();
        entityManager.persist(member);
        Genre genre1 = Genre.builder().genreId("1").genreName("코미디").build();
        Genre genre2 = Genre.builder().genreId("2").genreName("멜로").build();
        Genre genre3 = Genre.builder().genreId("3").genreName("로맨스").build();
        entityManager.persist(genre1);
        entityManager.persist(genre2);
        entityManager.persist(genre3);
        QGenre qGenre = QGenre.genre;
        List<Genre> genres = queryFactory.select(qGenre)
                .from(qGenre)
                .fetch();

        for (Genre genre : genres) {
            MemberAchievement memberAchievement = MemberAchievement.builder()
                    .member(member)
                    .genre(genre)
                    .build();

            entityManager.persist(memberAchievement);
            entityManager.flush();
            entityManager.clear();
        }


    }

}
