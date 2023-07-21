package com.ssafy.mereview.domain.member.repository;

import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.movie.entity.Genre;

import java.util.List;

public interface MemberQueryRepository {
    public Member searchByEmail(String email);

    public List<Genre> searchAllGenre();


}
