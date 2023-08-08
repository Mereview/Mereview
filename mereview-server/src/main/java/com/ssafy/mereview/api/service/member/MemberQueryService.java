package com.ssafy.mereview.api.service.member;

import com.ssafy.mereview.api.controller.member.dto.request.MemberLoginRequest;
import com.ssafy.mereview.api.service.member.dto.response.*;
import com.ssafy.mereview.api.service.review.dto.response.NotificationResponse;
import com.ssafy.mereview.common.util.jwt.JwtUtils;
import com.ssafy.mereview.domain.member.entity.*;
import com.ssafy.mereview.domain.member.repository.MemberQueryRepository;
import com.ssafy.mereview.domain.member.repository.MemberVisitQueryRepository;
import com.ssafy.mereview.domain.review.repository.query.NotificationQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {
    private final MemberQueryRepository memberQueryRepository;
    private final MemberVisitQueryRepository memberVisitQueryRepository;
    private final NotificationQueryRepository notificationQueryRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;


    public MemberLoginResponse login(MemberLoginRequest request) {
        log.debug("MemberLoginRequest : {}", request);

        Member searchMember = memberQueryRepository.searchByEmail(request.getEmail());

        if (searchMember == null || searchMember.getRole().equals(Role.DELETED)) {
            throw new NoSuchElementException("존재하지 않는 회원입니다.");
        }

        if (!passwordEncoder.matches(request.getPassword(), searchMember.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return createMemberLoginResponse(searchMember);
    }

    public List<MemberTierResponse> searchMemberTierByGenre(Long memberId, int genreNumber) {
        List<MemberTier> memberTiers = memberQueryRepository.searchMemberTierByGenre(memberId, genreNumber);

        return createMemberTierResponses(memberTiers);
    }

    private List<MemberTierResponse> createMemberTierResponses(List<MemberTier> memberTiers) {
        return memberTiers.stream().map(MemberTierResponse::of).collect(Collectors.toList());
    }


    private MemberLoginResponse createMemberLoginResponse(Member searchMember) {
        log.debug("searchMember : {}", searchMember);
        Map<String, String> token = jwtUtils.generateJwt(searchMember);
        return MemberLoginResponse.builder()
                .id(searchMember.getId())
                .email(searchMember.getEmail())
                .role(searchMember.getRole())
                .nickname(searchMember.getNickname())
                .profileImage(createProfileImageResponse(searchMember.getProfileImage()))
                .accessToken(token.get("accessToken"))
                .refreshToken(token.get("refreshToken"))
                .build();
    }

    public MemberResponse searchMemberInfo(Long id) {
        Member member = memberQueryRepository.searchById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));

        List<InterestResponse> interestResponses = searchInterestResponse(id);

        List<MemberTierResponse> memberTierResponses = searchMemberTierResponse(id);

        List<MemberAchievementResponse> memberAchievementResponses = searchMemberAchievementResponse(id);

        List<NotificationResponse> notificationResponses = notificationQueryRepository.searchByMemberId(id);


        int count = notificationQueryRepository.countByMemberId(id);

        return createMemberResponse(member, interestResponses, memberTierResponses, memberAchievementResponses, notificationResponses, count);
    }

    private MemberResponse createMemberResponse(Member member, List<InterestResponse> interestResponses, List<MemberTierResponse> memberTierResponses, List<MemberAchievementResponse> memberAchievementResponses, List<NotificationResponse> notificationResponses, int notificationCount) {
        return MemberResponse.builder()
                .id(member.getId())
                .following(member.getFollowing().size())
                .follower(member.getFollowers().size())
                .todayVisitCount(member.getMemberVisit().getTodayVisitCount())
                .totalVisitCount(member.getMemberVisit().getTotalVisitCount())
                .email(member.getEmail())
                .introduce(member.getIntroduce())
                .createdTime(member.getCreatedTime())
                .notificationCount(notificationCount)
                .notifications(notificationResponses)
                .nickname(member.getNickname())
                .gender(member.getGender())
                .birthDate(member.getBirthDate())
                .interests(interestResponses)
                .achievements(memberAchievementResponses)
                .tiers(memberTierResponses)
                .profileImage(createProfileImageResponse(member.getProfileImage()))
                .build();
    }

    private ProfileImageResponse createProfileImageResponse(ProfileImage profileImage) {
        log.debug("ProfileImage : {}", profileImage);
        return profileImage.getUploadFile() != null ? ProfileImageResponse.of(profileImage) : ProfileImageResponse.builder().build();
    }

    private List<MemberAchievementResponse> searchMemberAchievementResponse(Long id) {
        List<MemberAchievement> memberAchievements = memberQueryRepository.searchMemberAchievementByMemberId(id);

        return memberAchievements.stream().map(MemberAchievement::of).collect(Collectors.toList());
    }

    private List<MemberTierResponse> searchMemberTierResponse(Long id) {
        List<MemberTier> memberTiers = memberQueryRepository.searchUserTierByMemberId(id);
        return memberTiers.stream()
                .map(MemberTierResponse::of)
                .collect(Collectors.toList());
    }

    private List<InterestResponse> searchInterestResponse(Long id) {
        List<Interest> interests = memberQueryRepository.searchInterestByMemberId(id);
        return interests.stream()
                .map(Interest::of)
                .collect(Collectors.toList());
    }

    public List<FollowResponse> searchFollowingResponse(Long memberId) {
        Member member = memberQueryRepository.searchById(memberId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));


        return member.getFollowing().stream()
                .map(FollowResponse::of)
                .collect(Collectors.toList());
    }

    public List<FollowResponse> searchFollowerResponse(Long memberId) {
       Member member = memberQueryRepository.searchById(memberId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));

        return member.getFollowers().stream()
                .map(FollowResponse::of)
                .collect(Collectors.toList());
    }
}
