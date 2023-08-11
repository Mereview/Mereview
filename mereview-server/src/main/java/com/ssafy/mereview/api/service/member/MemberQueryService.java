package com.ssafy.mereview.api.service.member;

import com.ssafy.mereview.api.controller.member.dto.request.MemberLoginRequest;
import com.ssafy.mereview.api.service.member.dto.request.MemberServiceLoginRequest;
import com.ssafy.mereview.api.service.member.dto.response.*;
import com.ssafy.mereview.api.service.movie.dto.response.GenreResponse;
import com.ssafy.mereview.api.service.review.dto.response.*;
import com.ssafy.mereview.common.util.jwt.JwtUtils;
import com.ssafy.mereview.domain.member.entity.*;
import com.ssafy.mereview.domain.member.repository.MemberAchievementQueryRepository;
import com.ssafy.mereview.domain.member.repository.MemberFollowQueryRepository;
import com.ssafy.mereview.domain.member.repository.MemberQueryRepository;
import com.ssafy.mereview.domain.movie.entity.Movie;
import com.ssafy.mereview.domain.review.entity.*;
import com.ssafy.mereview.domain.review.repository.query.NotificationQueryRepository;
import com.ssafy.mereview.domain.review.repository.query.ReviewEvaluationQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.ssafy.mereview.common.util.ExperienceConstants.TIER_MAX_EXP_MAP;
import static com.ssafy.mereview.common.util.SizeConstants.COMMENT_ACHIEVEMENT_MAX_COUNT_MAP;
import static com.ssafy.mereview.common.util.SizeConstants.REVIEW_ACHIEVEMENT_MAX_COUNT_MAP;
import static com.ssafy.mereview.domain.member.entity.AchievementType.COMMENT;
import static com.ssafy.mereview.domain.member.entity.AchievementType.REVIEW;
import static com.ssafy.mereview.domain.review.entity.ReviewEvaluationType.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {
    private final MemberQueryRepository memberQueryRepository;
    private final MemberAchievementQueryRepository memberAchievementQueryRepository;
    private final NotificationQueryRepository notificationQueryRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReviewEvaluationQueryRepository reviewEvaluationQueryRepository;
    private final MemberFollowQueryRepository memberFollowQueryRepository;
    private final JwtUtils jwtUtils;


    public MemberLoginResponse login(MemberLoginRequest request) {
        log.debug("MemberLoginRequest : {}", request);

        Member searchMember = memberQueryRepository.searchByEmail(request.getEmail());

        if (searchMember == null || searchMember.getRole() == Role.DELETED) {
            throw new NoSuchElementException("존재하지 않는 회원입니다.");
        }

        if (!passwordEncoder.matches(request.getPassword(), searchMember.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return createMemberLoginResponse(searchMember);
    }

    public Boolean checkMember(MemberServiceLoginRequest request) {
        Member member = memberQueryRepository.searchByEmail(request.getEmail());
        return passwordEncoder.matches(request.getPassword(), member.getPassword());
    }

    public List<MemberTierResponse> searchMemberTierByGenre(Long memberId, int genreNumber) {
        List<MemberTier> memberTiers = memberQueryRepository.searchMemberTierByGenre(memberId, genreNumber);

        return createMemberTierResponses(memberTiers);
    }


    private List<MemberTierResponse> createMemberTierResponses(List<MemberTier> memberTiers) {

        for (MemberTier memberTier : memberTiers) {
            MemberTierResponse memberTierResponse = MemberTierResponse.of(memberTier);
            createExperiencePercent(memberTierResponse);
        }

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

        checkTiers(member);

        checkAchievements(id);

        List<InterestResponse> interestResponses = searchInterestResponse(id);

        List<MemberTierResponse> memberTierResponses = searchMemberTierResponse(id);

        List<MemberAchievementResponse> memberAchievementResponses = searchMemberAchievementResponse(id);

        List<ReviewResponse> reviewResponses = createReviewResponses(member.getReviews());

        return createMemberResponse(member, interestResponses, memberTierResponses, memberAchievementResponses, reviewResponses);
    }

    public List<FollowingResponse> searchFollowingResponse(Long memberId) {
        List<MemberFollow> memberFollow = memberFollowQueryRepository.searchFollowing(memberId);

        return memberFollow.stream()
                .map(FollowingResponse::of)
                .collect(Collectors.toList());
    }

    public List<FollowerResponse> searchFollowerResponse(Long memberId) {
        List<MemberFollow> memberFollow = memberFollowQueryRepository.searchFollower(memberId);

        return memberFollow.stream()
                .map(FollowerResponse::of)
                .collect(Collectors.toList());
    }

    public MemberDataResponse searchMemberData(Long id) {
        List<NotificationResponse> notificationResponses = notificationQueryRepository.searchByMemberId(id);
        int count = notificationQueryRepository.countByMemberId(id);
        Member member = memberQueryRepository.searchById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
        return MemberDataResponse.of(member, notificationResponses, count);
    }

    /** private method **/

    private void checkAchievements(Long memberId) {
        List<MemberAchievement> memberAchievements = memberAchievementQueryRepository.searchByMemberId(memberId);
        memberAchievements.forEach(memberAchievement -> {
            if (memberAchievement.getAchievementType().equals(REVIEW)) {
                int reviewCount = memberQueryRepository.searchReviewCountByMemberIdAndGenreId(memberId, memberAchievement.getGenre().getId());
                memberAchievement.checkAndPromoteAchievement(reviewCount);
            }else if(memberAchievement.getAchievementType().equals(COMMENT)){
                int commentCount = memberQueryRepository.searchCommentCountByMemberIdAndGenreId(memberId, memberAchievement.getGenre().getId());
                memberAchievement.checkAndPromoteAchievement(commentCount);
            }
        });

    }

    private MemberResponse createMemberResponse(Member member, List<InterestResponse> interestResponses, List<MemberTierResponse> memberTierResponses, List<MemberAchievementResponse> memberAchievementResponses, List<ReviewResponse> reviewResponses) {
        ProfileImage profileImage = member.getProfileImage();
        return MemberResponse.builder()
                .id(member.getId())
                .following(member.getFollowing().size())
                .follower(member.getFollowers().size())
                .reviews(reviewResponses)
                .todayVisitCount(member.getMemberVisit().getTodayVisitCount())
                .totalVisitCount(member.getMemberVisit().getTotalVisitCount())
                .email(member.getEmail())
                .introduce(member.getIntroduce())
                .createdTime(member.getCreatedTime())
                .nickname(member.getNickname())
                .gender(member.getGender())
                .birthDate(member.getBirthDate())
                .interests(interestResponses)
                .achievements(memberAchievementResponses)
                .tiers(memberTierResponses)
                .profileImage(createProfileImageResponse(profileImage))
                .build();
    }

    private ProfileImageResponse createProfileImageResponse(ProfileImage profileImage) {
        log.debug("ProfileImage : {}", profileImage);

        return profileImage != null ? ProfileImageResponse.of(profileImage) : null;
    }

    private List<MemberAchievementResponse> searchMemberAchievementResponse(Long id) {
        List<MemberAchievement> memberAchievements = memberQueryRepository.searchMemberAchievementByMemberId(id);


        return createMemberAchievementResponse(memberAchievements);
    }

    private List<MemberAchievementResponse> createMemberAchievementResponse(List<MemberAchievement> memberAchievements) {
        List<MemberAchievementResponse> memberAchievementResponses = new ArrayList<>();
        memberAchievements.stream().map(MemberAchievementResponse::of).forEach(memberAchievementResponse -> {
            updateAchievementPercent(memberAchievementResponse);
            memberAchievementResponses.add(memberAchievementResponse);
        });
        return memberAchievementResponses;
    }

    private void updateAchievementPercent(MemberAchievementResponse memberAchievementResponse) {
        AchievementType type = memberAchievementResponse.getAchievementType();
        double achievementPercent = 0;
        if (type == REVIEW) {
            achievementPercent = createReviewAchievementPercent(memberAchievementResponse);
        } else if (type == AchievementType.COMMENT) {
            achievementPercent = createCommentAchievementPercent(memberAchievementResponse);

        }
        memberAchievementResponse.updateAchievementPercent(achievementPercent);

    }

    private double createCommentAchievementPercent(MemberAchievementResponse memberAchievementResponse) {
        int achievementCount = memberAchievementResponse.getAchievementCount();
        int maxCount = COMMENT_ACHIEVEMENT_MAX_COUNT_MAP.get(memberAchievementResponse.getAchievementRank());

        return (int) ((double) achievementCount / maxCount * 10000) / 100.0;
    }

    private double createReviewAchievementPercent(MemberAchievementResponse memberAchievementResponse) {
        int achievementCount = memberAchievementResponse.getAchievementCount();
        int maxCount = REVIEW_ACHIEVEMENT_MAX_COUNT_MAP.get(memberAchievementResponse.getAchievementRank());

        return (int) ((double) achievementCount / maxCount * 10000) / 100.0;
    }

    private List<MemberTierResponse> searchMemberTierResponse(Long id) {
        List<MemberTier> memberTiers = memberQueryRepository.searchUserTierByMemberId(id);
        log.debug("memberTiers : {}", memberTiers);
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

    private List<ReviewResponse> createReviewResponses(List<Review> reviews) {
        return reviews.stream()
                .map(review -> {
                            Movie movie = review.getMovie();
                            Member writeMember = review.getMember();
                            return ReviewResponse.builder()
                                    .reviewId(review.getId())
                                    .reviewTitle(review.getTitle())
                                    .hits(review.getHits())
                                    .highlight(review.getHighlight())
                                    .movieRecommendType(review.getType())
                                    .commentCount(review.getComments().size())
                                    .positiveCount(getPositiveCount(review.getId()))
                                    .funCount(getTypeCountByReviewAndType(FUN, review.getId()))
                                    .usefulCount(getTypeCountByReviewAndType(USEFUL, review.getId()))
                                    .badCount(getTypeCountByReviewAndType(BAD, review.getId()))
                                    .backgroundImageResponse(createBackgroundImageResponse(review.getBackgroundImage()))
                                    .createdTime(review.getCreatedTime())
                                    .memberId(writeMember.getId())
                                    .nickname(writeMember.getNickname())
                                    .profileImage(getProfileImageResponse(writeMember.getProfileImage()))
                                    .movieId(movie.getId())
                                    .movieTitle(movie.getTitle())
                                    .movieReleaseDate(movie.getReleaseDate())
                                    .genreResponse(GenreResponse.of(review.getGenre()))
                                    .build();
                        }
                ).collect(Collectors.toList());
    }

    private int getPositiveCount(Long reviewId) {
        return getTypeCountByReviewAndType(FUN, reviewId) + getTypeCountByReviewAndType(USEFUL, reviewId);
    }

    private int getTypeCountByReviewAndType(ReviewEvaluationType type, Long reviewId) {
        return reviewEvaluationQueryRepository.getCountByReviewIdAndType(reviewId, type);
    }

    private BackgroundImageResponse createBackgroundImageResponse(BackgroundImage backgroundImage) {
        if (backgroundImage == null) {
            return null;
        }
        return BackgroundImageResponse.of(backgroundImage);
    }

    private ProfileImageResponse getProfileImageResponse(ProfileImage profileImage) {
        if (profileImage == null) {
            return null;
        }
        return ProfileImageResponse.of(profileImage);
    }

    private void createExperiencePercent(MemberTierResponse memberTierResponse) {
        int funExperiencePercent = (int) ((double) memberTierResponse.getFunExperience() / TIER_MAX_EXP_MAP.get(memberTierResponse.getFunTier()) * 100);
        int usefulExperiencePercent = (int) ((double) memberTierResponse.getUsefulExperience() / TIER_MAX_EXP_MAP.get(memberTierResponse.getUsefulTier()) * 100);

        memberTierResponse.createExperiencePercent(funExperiencePercent, usefulExperiencePercent);

    }

    private static void checkTiers(Member member) {
        member.getMemberTiers().forEach(memberTier -> {
            memberTier.checkAndPromoteUsefulTier();
            memberTier.checkAndPromoteFunTier();
        });
    }
}
