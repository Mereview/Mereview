package com.ssafy.mereview.domain.member.repository;

import com.ssafy.mereview.domain.member.entity.Member;

public interface MemberQueryRepository {
    public Member searchByEmail(String email);



}
