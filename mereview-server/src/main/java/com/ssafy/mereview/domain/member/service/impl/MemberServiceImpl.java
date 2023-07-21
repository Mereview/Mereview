package com.ssafy.mereview.domain.member.service.impl;

import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.entity.MemberAchievement;
import com.ssafy.mereview.domain.member.entity.Role;
import com.ssafy.mereview.domain.member.repository.MemberAchievementRepository;
import com.ssafy.mereview.domain.member.repository.MemberQueryRepository;
import com.ssafy.mereview.domain.member.repository.MemberRepository;
import com.ssafy.mereview.domain.member.repository.impl.MemberQueryRepositoryImpl;
import com.ssafy.mereview.domain.member.service.MemberService;
import com.ssafy.mereview.domain.member.service.dto.SaveMemberDto;
import com.ssafy.mereview.domain.movie.entity.Genre;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    private final MemberQueryRepository memberQueryRepository;

    private final MemberAchievementRepository memberAchievementRepository;

    @Override
    public Long saveMember(SaveMemberDto dto) {

        if (memberQueryRepository.searchByEmail(dto.getEmail()) != null) {
            return -1L;
        } else {
            Member member = Member.builder()
                    .email(dto.getEmail())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .build();
            memberRepository.save(member);
            List<Genre> genres = memberQueryRepository.searchAllGenre();
            saveAchievements(member, genres);
            Long id = member.getId();
            return id;
        }


    }

    private void saveAchievements(Member member, List<Genre> genres) {
        List<MemberAchievement> achievements = new ArrayList<>();
        for (Genre genre : genres) {
            MemberAchievement memberAchievement = MemberAchievement.builder()
                    .member(member)
                    .genre(genre)
                    .build();
            achievements.add(memberAchievement);
        }
        memberAchievementRepository.saveAll(achievements);
    }

}
