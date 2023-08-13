package com.ssafy.mereview.api.service.member;

import com.ssafy.mereview.api.controller.member.dto.request.InterestRequest;
import com.ssafy.mereview.api.controller.member.dto.request.MemberIntroduceRequest;
import com.ssafy.mereview.api.service.member.dto.request.EmailCheckCode;
import com.ssafy.mereview.api.service.member.dto.request.InterestServiceRequest;
import com.ssafy.mereview.api.service.member.dto.request.MemberCreateServiceRequest;
import com.ssafy.mereview.api.service.member.dto.request.MemberUpdateServiceRequest;
import com.ssafy.mereview.api.service.member.dto.response.MemberFollowResponse;
import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.common.util.jwt.JwtUtils;
import com.ssafy.mereview.domain.member.entity.*;
import com.ssafy.mereview.domain.member.repository.*;
import com.ssafy.mereview.domain.movie.entity.Genre;
import com.ssafy.mereview.domain.movie.repository.command.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.ssafy.mereview.common.util.EmailConstants.EMAIL_CHECK_CODE_HASH_MAP;

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

    private final MemberVisitCountRepository memberVisitCountRepository;

    private final MemberInterestRepository memberInterestRepository;

    private final ProfileImageRepository profileImageRepository;

    private final MemberTierRepository memberTierRepository;

    private final GenreRepository genreRepository;

    private final MemberFollowRepository memberFollowRepository;

    private final MemberFollowQueryRepository memberFollowQueryRepository;

    public Long createMember(MemberCreateServiceRequest request, UploadFile uploadFile) {
        EmailCheckCode emailCheckCode = EMAIL_CHECK_CODE_HASH_MAP.getOrDefault(request.getEmail(), null);

        emailCheck(request, emailCheckCode);

        log.debug("request = {}", request);
        Member member = request.toEntity(passwordEncoder.encode(request.getPassword()));
        log.debug("member = " + member.getEmail());

        Member savedMember = memberRepository.save(member);
        log.debug("savedMember = " + savedMember.getEmail());

        if (uploadFile != null) {
            createProfileImage(uploadFile, savedMember.getId());
        }

        // 방문자 수 초기화
        createVisitCount(member);

        // 회원 관심사 초기화
        createInterests(request.getInterests(), member);

        // 회원 티어 초기화
        createTier(member);

        createAchievement(member);

        EMAIL_CHECK_CODE_HASH_MAP.remove(request.getEmail());

        return savedMember.getId();
    }

    public void searchExistMember(MemberCreateServiceRequest request) {
        Member existingMember = memberQueryRepository.searchByEmail(request.getEmail());
        if (existingMember != null) {
            throw new DuplicateKeyException("이미 존재하는 회원입니다.");
        }

        if (memberQueryRepository.searchByNickname(request.getNickname()) != null) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
    }

    public void searchExistedEmailCheck(String email){
        Member existingMember = memberQueryRepository.searchByEmail(email);
        if (existingMember != null) {
            throw new NoSuchElementException("이미 가입된 회원입니다.");
        }
    }

    private void emailCheck(MemberCreateServiceRequest request, EmailCheckCode emailCheckCode) {
        if (emailCheckCode == null) {
            throw new IllegalArgumentException("인증 코드가 존재하지 않습니다.");

        }
        if (!jwtUtils.validateJwt(emailCheckCode.getJwtToken())) {
            throw new IllegalArgumentException("인증 코드가 만료되었습니다.");
        }

        if (!emailCheckCode.getVerificationCode().equals(request.getVerificationCode())) {
            throw new IllegalArgumentException("인증 코드가 일치하지 않습니다.");
        }

    }

    public Long updateMember(Long memberId, MemberUpdateServiceRequest request) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
        log.debug("update request : {}", request);

        List<InterestRequest> interestRequests = request.getInterestRequests();
        log.debug("interestRequests = " + interestRequests);

        member.updateNickname(request.getNickname());
        log.debug("Member nickname 확인 : {}", member.getNickname());
        updateInterests(interestRequests, member);
        log.debug("Member interest 확인 : {}", member.getInterests());
        return member.getId();
    }


    public void deleteMember(Long id, String token) {
        // jwt 토큰으로 현재 로그인한 유저인지 확인 후 회원탈퇴 진행
        Member member = memberRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));

        if (member.getRole().equals(Role.DELETED)) {
            throw new IllegalArgumentException("이미 탈퇴한 회원입니다.");
        }

        String memberInToken = jwtUtils.getUsernameFromJwt(token);

        log.debug("memberInToken = {}", memberInToken);
        log.debug("member.getEmail() = {}", member.getEmail());

        // 현재 로그인한 유저가 맞는지 확인
        if (member.getEmail().equals(memberInToken)) {
            member.delete();
        } else {
            throw new IllegalArgumentException("현재 로그인한 유저가 아닙니다.");
        }
    }

    public void updateViewCount(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(NoSuchElementException::new);
        log.debug("조회수 : {}", member.getMemberVisit());
        member.getMemberVisit().updateVisitCount();
    }

    public MemberFollowResponse createFollow(Long targetId, Long currentMemberId) {
        // 팔로우 할 유저

        Member target = memberRepository.findById(targetId)
                .orElseThrow(() -> new IllegalArgumentException("Follower not found!"));

        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new IllegalArgumentException("Following not found!"));

        // 팔로워가 현재 유저인 타겟(내가 팔로우하는 타겟)이 존재할 경우
        MemberFollow existFollow = memberFollowQueryRepository.searchByTargetAndCurrentMember(targetId, currentMemberId);
        log.debug("memberFollow = {}", existFollow);

        if (existFollow != null) {
            memberFollowRepository.delete(existFollow);
            return MemberFollowResponse.of(existFollow, "unfollow");
        }

        return follow(target, currentMember);

    }

    public Long updateMemberIntroduce(MemberIntroduceRequest request, String token) {
        log.debug("updateMemberIntroduce request = {}", request);

        Member member = memberRepository.findById(request.getId()).orElseThrow(NoSuchElementException::new);
        String memberInToken = jwtUtils.getUsernameFromJwt(token);

        // 현재 로그인한 유저가 맞는지 확인
        if (member.getEmail().equals(memberInToken)) {
            member.updateIntroduce(request.getIntroduce());
        } else {
            throw new IllegalArgumentException("현재 로그인한 유저가 아닙니다.");
        }
        return member.getId();
    }

    public void updateProfileImage(Long memberId, UploadFile uploadFile) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        member.updateProfileImage(uploadFile);
    }

    //***************private method*****************//

    private void createVisitCount(Member member) {
        MemberVisit memberVisitCount = MemberVisit.builder()
                .member(member)
                .build();
        memberVisitCountRepository.save(memberVisitCount);
    }

    private void createInterests(List<InterestServiceRequest> requests, Member member) {
        List<Interest> interests = new ArrayList<>();
        log.debug("requests = " + requests);

        for (InterestServiceRequest request : requests) {
            Genre genre = genreRepository.findById(request.getGenreId()).orElseThrow(NoSuchElementException::new);
            Interest interest = Interest.builder().member(Member.builder().id(member.getId()).build())
                    .genre(genre).build();

            interests.add(interest);
        }
        memberInterestRepository.saveAll(interests);

    }

    public void updateInterests(List<InterestRequest> requests, Member member) {

        Member updateMember = memberRepository.findById(member.getId()).orElseThrow(NoSuchElementException::new);
        List<Interest> interests = updateMember.getInterests();
        interests.clear();
        log.debug("member interests : {}", requests);
        for (InterestRequest interestRequest : requests) {
            Genre genre = genreRepository.findById(interestRequest.getGenreId()).orElseThrow(NoSuchElementException::new);
            Interest interest = Interest.builder().member(Member.builder().id(updateMember.getId()).build())
                    .genre(genre).build();
            interests.add(interest);
        }
        log.debug("Member interests : {}", interests);
        member.update(interests);

        log.debug("Member interests : {}", interests);

        memberRepository.save(updateMember);

        log.debug("member interests : {}", member.getInterests().size());

    }

    private void createTier(Member member) {
        List<Genre> genres = memberQueryRepository.searchAllGenre();
        log.debug("genres = " + genres.size());
        List<MemberTier> memberTiers = new ArrayList<>();
        genres.forEach(genre -> memberTiers.add(MemberTier.builder()
                .member(member)
                .genre(genre)
                .build()));
        log.debug("memberTiers = " + memberTiers.size());

        memberTierRepository.saveAll(memberTiers);
    }

    private void createAchievement(Member member) {
        // TODO: 2023-08-03 장르 레포지토리로 바꾸기
        List<Genre> genres = genreRepository.findAll();

        List<MemberAchievement> memberAchievements = genres.stream().map(genre -> MemberAchievement.builder()
                .member(member)
                .genre(genre)
                .build()).collect(Collectors.toList());
        log.debug("memberAchievements = " + memberAchievements.size());

        memberAchievementRepository.saveAll(memberAchievements);
    }

    private void createProfileImage(UploadFile uploadFile, Long saveId) {
        ProfileImage profileImage = ProfileImage.builder()
                .member(Member.builder().id(saveId).build())
                .uploadFile(uploadFile)
                .build();
        profileImageRepository.save(profileImage);
    }

    private MemberFollowResponse follow(Member target, Member currentMember) {
        MemberFollow memberFollow = MemberFollow.builder()
                .member(currentMember)
                .targetMember(target)
                .build();

        memberFollowRepository.save(memberFollow);

        return MemberFollowResponse.of(memberFollow, "follow");
    }
}
