package com.ssafy.mereview.domain.member.repository;

import com.ssafy.mereview.domain.member.entity.MemberFollow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberFollowRepository extends JpaRepository<MemberFollow, Long> {
}
