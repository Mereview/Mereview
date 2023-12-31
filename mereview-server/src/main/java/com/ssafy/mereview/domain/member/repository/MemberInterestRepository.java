package com.ssafy.mereview.domain.member.repository;

import com.ssafy.mereview.domain.member.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberInterestRepository extends JpaRepository<Interest, Long> {
}
