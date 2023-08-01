package com.ssafy.mereview.domain.member.repository;

import com.ssafy.mereview.domain.member.entity.Interest;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.entity.MemberAchievement;
import com.ssafy.mereview.domain.member.entity.MemberTier;
import com.ssafy.mereview.domain.movie.entity.Genre;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberQueryRepository {
    public Member searchByEmail(String email);

    public List<Genre> searchAllGenre();

    public Genre searchGenreByGenreName(String genreName);

    public List<Interest> searchInterestByMemberId(Long memberId);

    public List<MemberTier> searchUserTierByMemberId(Long memberId);

    public List<MemberAchievement> searchMemberAchievementByMemberId(Long memberId);




}
