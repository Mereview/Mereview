package com.ssafy.mereview.domain.member.repository;

import com.ssafy.mereview.domain.member.entity.MemberVisit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberVisitCountRepository extends JpaRepository<MemberVisit, Long>{
}
