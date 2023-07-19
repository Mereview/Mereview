package com.ssafy.mereview.domain.member.entity;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
public class MemberInterestTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JPAQueryFactory queryFactory;

    @Test
    public void MemberAddInterestTest() {
        Member member = new Member("duljji@naver.com", "1234", "duljji", null);
        Interest interest = new Interest(member, "코미디", "1");
        Interest interest2 = new Interest(member, "멜호", "2");
        entityManager.persist(interest);
        entityManager.persist(interest2);
        member.addInterest(interest);
        member.addInterest(interest2);
        entityManager.persist(member);
        entityManager.flush();
        entityManager.clear();

        QMember q = QMember.member;
        QInterest i = QInterest.interest1;
        List<Tuple> tuples  = queryFactory.select(q, i).from(q).rightJoin(q.interests, i).fetchJoin().fetch();


    }
}
