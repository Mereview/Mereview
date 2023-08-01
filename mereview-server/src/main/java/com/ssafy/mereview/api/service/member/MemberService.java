package com.ssafy.mereview.api.service.member;

import com.ssafy.mereview.api.controller.member.dto.request.MemberLoginRequest;
import com.ssafy.mereview.api.service.member.dto.request.MemberCreateServiceRequest;
import com.ssafy.mereview.api.service.member.dto.response.*;
import com.ssafy.mereview.api.service.review.dto.request.ReviewCreateServiceRequest;
import com.ssafy.mereview.common.util.jwt.JwtUtils;
import com.ssafy.mereview.domain.member.entity.*;
import com.ssafy.mereview.domain.member.repository.*;
import com.ssafy.mereview.domain.movie.entity.Genre;
import com.ssafy.mereview.domain.movie.repository.GenreRepository;
import com.ssafy.mereview.domain.review.entity.BackgroundImage;
import com.ssafy.mereview.domain.review.entity.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    private final MemberRepository memberRepository;

    private final MemberQueryRepository memberQueryRepository;

    private final MemberAchievementRepository memberAchievementRepository;

    private final MemberInterestRepository memberInterestRepository;

    private final ProfileImageRepository profileImageRepository;

    private final MemberTierRepository memberTierRepository;

    private final GenreRepository genreRepository;

    public Long createMember(MemberCreateServiceRequest request) {

        Member existingMember = memberQueryRepository.searchByEmail(request.getEmail());
        if (existingMember != null) {
            throw new DuplicateKeyException("이미 존재하는 회원입니다.");
        }

        Member member = Member.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))

                .build();
        log.debug("member = " + member.getEmail());

        Member savedMember = memberRepository.save(member);
        log.debug("savedMember = " + savedMember.getEmail());

        profileImageRepository.save(createProfileImage(request, savedMember.getId()));


        //회원 관심사 초기화
        createInterest(request, member);

        //회원 티어 초기화
        createTier(member);
        createAchievement(member);

        return savedMember.getId();

    }

    public MemberLoginResponse login(MemberLoginRequest request) {
        Member searchMember = memberQueryRepository.searchByEmail(request.getEmail());
        if (searchMember == null) {
            throw new NoSuchElementException("존재하지 않는 회원입니다.");
        }
        if (!passwordEncoder.matches(request.getPassword(), searchMember.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return getMemberLoginResponse(searchMember);

    }

    private MemberLoginResponse getMemberLoginResponse(Member searchMember) {
        Map<String, String> token = jwtUtils.generateJwt(searchMember);

        return MemberLoginResponse.builder()
                .memberResponse(searchMemberInfo(searchMember.getId()))
                .token(token)
                .build();
    }

    public MemberResponse searchMemberInfo(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(NoSuchElementException::new);

        List<InterestResponse> interestResponses = searchInterestResponse(id);

        List<MemberTierResponse> memberTierResponses = searchMemberTierResponse(id);

        List<MemberAchievementResponse> memberAchievementResponses = searchMemberAchievementReponse(id);

        return createMemberResponse(member, interestResponses, memberTierResponses, memberAchievementResponses);

    }


    public void follow(Long targetId, Long currentUserId) {
        //팔로우 할 유저
        Member target = memberRepository.findById(targetId)
                .orElseThrow(() -> new IllegalArgumentException("Follower not found!"));

        Member currentMember = memberRepository.findById(currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("Following not found!"));

        //팔로워가 현재 유저인 타겟(내가 팔로우하는 타겟)이 존재할 경우
        if (currentMember.getFollowing().contains(target)) {
            currentMember.getFollowing().remove(target);
            target.getFollowers().remove(currentMember);
            return;
        }
        currentMember.getFollowing().add(target);
        target.getFollowers().add(currentMember);
    }


    //***************private method*****************//

    private void createInterest(MemberCreateServiceRequest dto, Member member) {
        List<Interest> interests = new ArrayList<>();

        dto.getInterestRequests().stream().map(interestRequest ->
                        genreRepository.findById(interestRequest.getGenreId()).orElseThrow(NoSuchElementException::new))
                .map(genre -> Interest.builder()
                        .member(member)
                        .genre(genre)
                        .build()).forEach(interests::add);

        log.debug("interests = " + interests.size());

        memberInterestRepository.saveAll(interests);
    }

    private void createTier(Member member) {
        List<Genre> genres = memberQueryRepository.searchAllGenre();

        List<MemberTier> memberTiers = new ArrayList<>();
        genres.forEach(genre -> memberTiers.add(MemberTier.builder()
                .member(member)
                .genre(genre)
                .build()));
        log.debug("memberTiers = " + memberTiers.size());

        memberTierRepository.saveAll(memberTiers);
    }

    private void createAchievement(Member member) {
        List<Genre> genres = memberQueryRepository.searchAllGenre();

        List<MemberAchievement> memberAchievements = genres.stream().map(genre -> MemberAchievement.builder()
                .member(member)
                .genre(genre)
                .build()).collect(Collectors.toList());
        log.debug("memberAchievements = " + memberAchievements.size());

        memberAchievementRepository.saveAll(memberAchievements);
    }

    private MemberResponse createMemberResponse(Member member, List<InterestResponse> interestResponses, List<MemberTierResponse> memberTierResponses, List<MemberAchievementResponse> memberAchievementResponses) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .interests(interestResponses)
                .achievements(memberAchievementResponses)
                .tiers(memberTierResponses)
                .build();
    }

    private List<MemberAchievementResponse> searchMemberAchievementReponse(Long id) {
        List<MemberAchievement> memberAchievements = memberQueryRepository.searchMemberAchievementByMemberId(id);

        return memberAchievements.stream().map(MemberAchievement::of).collect(Collectors.toList());
    }

    private List<MemberTierResponse> searchMemberTierResponse(Long id) {
        List<MemberTier> memberTiers = memberQueryRepository.searchUserTierByMemberId(id);
        return memberTiers.stream()
                .map(MemberTier::of)
                .collect(Collectors.toList());
    }

    private List<InterestResponse> searchInterestResponse(Long id) {
        List<Interest> interests = memberQueryRepository.searchInterestByMemberId(id);
        return interests.stream()
                .map(Interest::of)
                .collect(Collectors.toList());
    }

    private ProfileImage createProfileImage(MemberCreateServiceRequest request, Long saveId) {
        return ProfileImage.builder()
                .member(Member.builder().id(saveId).build())
                .uploadFile(request.getUploadFile())
                .build();
    }
}
