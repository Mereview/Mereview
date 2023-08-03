package com.ssafy.mereview.api.service.member;

import com.ssafy.mereview.api.controller.member.dto.request.MemberLoginRequest;
import com.ssafy.mereview.api.service.member.dto.response.*;
import com.ssafy.mereview.common.util.jwt.JwtUtils;
import com.ssafy.mereview.domain.member.entity.*;
import com.ssafy.mereview.domain.member.repository.MemberQueryRepository;
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

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;


    public MemberLoginResponse login(MemberLoginRequest request) {
        log.debug("MemberLoginRequest : {}", request);

        Member searchMember = memberQueryRepository.searchByEmail(request.getEmail());

        if (searchMember == null) {
            throw new NoSuchElementException("존재하지 않는 회원입니다.");
        }
        if (!passwordEncoder.matches(request.getPassword(), searchMember.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return createMemberLoginResponse(searchMember);
    }

    private MemberLoginResponse createMemberLoginResponse(Member searchMember) {
        log.debug("searchMember : {}", searchMember);

        Map<String, String> token = jwtUtils.generateJwt(searchMember);
        return MemberLoginResponse.builder()
                .id(searchMember.getId())
                .email(searchMember.getEmail())
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

        List<MemberAchievementResponse> memberAchievementResponses = searchMemberAchievementReponse(id);

        return createMemberResponse(member, interestResponses, memberTierResponses, memberAchievementResponses);
    }

    private MemberResponse createMemberResponse(Member member, List<InterestResponse> interestResponses, List<MemberTierResponse> memberTierResponses, List<MemberAchievementResponse> memberAchievementResponses) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .interests(interestResponses)
                .achievements(memberAchievementResponses)
                .tiers(memberTierResponses)
                .profileImage(createProfileImageResponse(member.getProfileImage()))
                .build();
    }

    private ProfileImageResponse createProfileImageResponse(ProfileImage profileImage) {
        log.debug("ProfileImage : {}", profileImage);
        return profileImage.getUploadFile() == null ? ProfileImageResponse.builder().build() : profileImage.of();
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
}
