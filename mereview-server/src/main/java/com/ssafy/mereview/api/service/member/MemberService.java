package com.ssafy.mereview.api.service.member;

import com.ssafy.mereview.api.controller.member.dto.request.InterestRequest;
import com.ssafy.mereview.api.service.member.dto.request.SaveMemberServiceRequest;
import com.ssafy.mereview.api.service.member.dto.response.InterestResponse;
import com.ssafy.mereview.api.service.member.dto.response.MemberAchievementResponse;
import com.ssafy.mereview.api.service.member.dto.response.MemberResponse;
import com.ssafy.mereview.api.service.member.dto.response.MemberTierResponse;
import com.ssafy.mereview.domain.member.entity.*;
import com.ssafy.mereview.domain.member.repository.*;
import com.ssafy.mereview.domain.movie.entity.Genre;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    private final MemberQueryRepository memberQueryRepository;

    private final MemberAchievementRepository memberAchievementRepository;

    private final MemberInterestRepository memberInterestRepository;

    private final MemberTierRepository memberTierRepository;

    private final AchievementQueryRepository achievementQueryRepository;

    public Long saveMember(SaveMemberServiceRequest dto) {

        if (memberQueryRepository.searchByEmail(dto.getEmail()) != null) {
            return -1L;
        } else {
            Member member = Member.builder()
                    .email(dto.getEmail())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .build();
            System.out.println("member = " + member.getEmail());
            Member savedMember = memberRepository.save(member);


            saveInterest(dto, member);

            //회원 가입 시 Tier, Achievement 초기화
            saveTierAndAchievement(member);

            return savedMember.getId();

        }


    }

    private void saveInterest(SaveMemberServiceRequest dto, Member member) {
        for (InterestRequest genreRequest : dto.getInterestRequests()) {
            Genre genre = memberQueryRepository.searchGenreByGenreName(genreRequest.getGenreName());

            Interest interest = Interest.builder()
                    .member(member)
                    .genre(genre)
                    .build();

            memberInterestRepository.save(interest);
        }
    }


    private void saveTierAndAchievement(Member member) {
        List<Genre> genres = memberQueryRepository.searchAllGenre();
        List<MemberTier> memberTierLIst = new ArrayList<>();
        List<MemberAchievement> memberAchievementList = new ArrayList<>();

        for (Genre genre : genres) {
            makeMemberTier(member, memberTierLIst, genre);
            makeAchievement(member, memberAchievementList, genre);

        }

        memberTierRepository.saveAll(memberTierLIst);
        memberAchievementRepository.saveAll(memberAchievementList);

    }

    private void makeAchievement(Member member, List<MemberAchievement> memberAchievementList, Genre genre) {
        List<Achievement> achievementList = achievementQueryRepository.searchAllAchievement();

        for (Achievement achievement : achievementList) {
            MemberAchievement memberAchievement = MemberAchievement.builder()
                    .member(member)
                    .genre(genre)
                    .achievement(achievement)
                    .build();
            memberAchievementList.add(memberAchievement);

        }


    }

    private void makeMemberTier(Member member, List<MemberTier> memberTierLIst, Genre genre) {
        MemberTier memberTier = MemberTier.builder()
                .member(member)
                .genre(genre)
                .build();

        memberTierLIst.add(memberTier);
    }

    public MemberResponse getLoginInfo(Long id){
        Member member = memberRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return member.of();
    }



    public MemberResponse getMemberInfo(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(NoSuchElementException::new);


        List<Interest> interests = memberQueryRepository.searchInterestByMemberId(id);
        List<InterestResponse> interestResponseList = new ArrayList<>();
        for (Interest interest : interests) {
            InterestResponse interestResponse = InterestResponse.builder()
                    .genreName(interest.getGenre().getGenreName())
                    .build();
            interestResponseList.add(interestResponse);
        }

        List<MemberTier> memberTiers = memberQueryRepository.searchUserTierByMemberId(id);
        List<MemberTierResponse> memberTierResponseList = new ArrayList<>();
        for (MemberTier memberTier : memberTiers) {
            MemberTierResponse memberTierResponse = MemberTierResponse.builder()
                    .genreName(memberTier.getGenre().getGenreName())
                    .usefulTier(memberTier.getUsefulTier())
                    .funTier(memberTier.getFunTier())
                    .usefulExperience(memberTier.getUsefulExperience())
                    .funExperience(memberTier.getFunExperience())
                    .build();
            memberTierResponseList.add(memberTierResponse);
        }

        List<MemberAchievement> memberAchievements = memberQueryRepository.searchMemberAchievementByMemberId(id);
        System.out.println("memberAchievements = " + memberAchievements);
        List<MemberAchievementResponse> memberAchievementResponseList = new ArrayList<>();
        for (MemberAchievement memberAchievement : memberAchievements) {
            MemberAchievementResponse memberAchievementResponse = MemberAchievementResponse.builder()
                    .genreName(memberAchievement.getGenre().getGenreName())
                    .achievementName(memberAchievement.getAchievement().getAchievementName())
                    .achievementRank(memberAchievement.getAchievementRank())
                    .build();

            memberAchievementResponseList.add(memberAchievementResponse);
        }

        MemberResponse memberResponse = MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .interests(interestResponseList)
                .achievements(memberAchievementResponseList)
                .tiers(memberTierResponseList)
                .build();

        return memberResponse;
    }

    public void follow(Long targetId, Long currentUserId) {
        //팔로우 할 유저
        Member target = memberRepository.findById(targetId)
                .orElseThrow(() -> new IllegalArgumentException("Follower not found!"));

        Member currentMember = memberRepository.findById(currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("Following not found!"));

        //팔로워가 현재 유저인 타겟(내가 팔로우하는 타겟)이 존재할 경우
        if(currentMember.getFollowing().contains(target)) {
            currentMember.getFollowing().remove(target);
            target.getFollowers().remove(currentMember);
            memberRepository.save(currentMember);
            memberRepository.save(target);
            return;
        }
        currentMember.getFollowing().add(target);
        target.getFollowers().add(currentMember);

        memberRepository.save(currentMember);
        memberRepository.save(target);

    }


}
