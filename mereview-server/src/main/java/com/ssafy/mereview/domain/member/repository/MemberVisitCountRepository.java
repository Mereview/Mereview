package com.ssafy.mereview.domain.member.repository;

import com.ssafy.mereview.domain.member.entity.MemberVisitCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberVisitCountRepository extends JpaRepository<MemberVisitCount, Long>{
}
