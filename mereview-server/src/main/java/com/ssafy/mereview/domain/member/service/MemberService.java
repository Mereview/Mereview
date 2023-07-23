package com.ssafy.mereview.domain.member.service;

import com.ssafy.mereview.domain.member.controller.dto.res.GenreResponse;
import com.ssafy.mereview.domain.member.controller.dto.res.InterestResponse;
import com.ssafy.mereview.domain.member.controller.dto.res.MemberResponse;
import com.ssafy.mereview.domain.member.entity.Interest;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.entity.MemberAchievement;
import com.ssafy.mereview.domain.member.repository.MemberAchievementRepository;
import com.ssafy.mereview.domain.member.repository.MemberInterestRepository;
import com.ssafy.mereview.domain.member.repository.MemberQueryRepository;
import com.ssafy.mereview.domain.member.repository.MemberRepository;
import com.ssafy.mereview.domain.member.service.dto.SaveMemberDto;
import com.ssafy.mereview.domain.movie.entity.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService{

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    private final MemberQueryRepository memberQueryRepository;

    private final MemberAchievementRepository memberAchievementRepository;

    private final MemberInterestRepository memberInterestRepository;

    public Long saveMember(SaveMemberDto dto) {

        if (memberQueryRepository.searchByEmail(dto.getEmail()) != null) {
            return -1L;
        } else {
            Member member = Member.builder()
                    .email(dto.getEmail())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .build();
            memberRepository.save(member);
            System.out.println("dto.getGenres() = " + dto.getGenres());
            saveInterests(member, dto.getGenres());
            List<Genre> genres = memberQueryRepository.searchAllGenre();
            saveAchievements(member, genres);
            Long id = member.getId();
            return id;
        }


    }

    public void saveInterests(Member member, List<Genre> genres) {
        List<Interest> interests = new ArrayList<>();
        for(Genre genre : genres){
            Interest interest = Interest.builder()
                    .member(member)
                    .genre(genre)
                    .build();
            interests.add(interest);
        }
        memberInterestRepository.saveAll(interests);
    }

    public void saveAchievements(Member member, List<Genre> genres) {
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

    public MemberResponse getMemberInfo(Long id){
        List<Interest> interests = memberQueryRepository.searchInterestByMemberId(id);
        List<GenreResponse> genreResponses = new ArrayList<>();
        for (Interest interest : interests) {
            GenreResponse genreResponse = GenreResponse.builder()
                    .genreName(interest.getGenre().getGenreName())
                    .build();
            genreResponses.add(genreResponse);
        }


        Member member =  memberRepository.findById(id).get();
        System.out.println("member.getEmail() = " + member.getEmail());
        MemberResponse memberResponse = MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .genres(genreResponses)
                .build();

        return memberResponse;

    }


}
