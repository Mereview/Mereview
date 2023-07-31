package com.ssafy.mereview.domain.member.repository;

import com.ssafy.mereview.domain.member.entity.MemberTier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTierRepository extends JpaRepository<MemberTier, Long> {
}
