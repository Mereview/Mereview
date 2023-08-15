package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.review.dto.request.KeywordCreateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.request.KeywordUpdateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.request.ReviewCreateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.request.ReviewUpdateServiceRequest;
import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.repository.MemberAchievementQueryRepository;
import com.ssafy.mereview.domain.member.repository.MemberInterestQueryRepository;
import com.ssafy.mereview.domain.review.entity.BackgroundImage;
import com.ssafy.mereview.domain.review.entity.Keyword;
import com.ssafy.mereview.domain.review.entity.Notification;
import com.ssafy.mereview.domain.review.entity.Review;
import com.ssafy.mereview.domain.review.repository.command.BackgroundImageRepository;
import com.ssafy.mereview.domain.review.repository.command.KeywordRepository;
import com.ssafy.mereview.domain.review.repository.command.NotificationRepository;
import com.ssafy.mereview.domain.review.repository.command.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final KeywordRepository keywordRepository;
    private final BackgroundImageRepository backgroundImageRepository;
    private final MemberInterestQueryRepository interestQueryRepository;
    private final NotificationRepository notificationRepository;
    private final MemberAchievementQueryRepository memberAchievementQueryRepository;

    private static final int MEMBER_LIMIT_COUNT = 100;

    public Long create(ReviewCreateServiceRequest request, UploadFile uploadFile) {
        Long saveId = reviewRepository.save(request.toEntity()).getId();

        updateReviewAchievementCount(request);

        List<Keyword> keywords = createKeywords(saveId, request.getKeywordServiceRequests());
        keywordRepository.saveAll(keywords);

        if (uploadFile != null) {
            createBackgroundImage(saveId, uploadFile);
        }

        List<Notification> notifications = createNotifications(request, saveId);
        notificationRepository.saveAll(notifications);

        return saveId;
    }

    public Long update(ReviewUpdateServiceRequest request) {
        Review review = reviewRepository.findById(request.getReviewId())
                .orElseThrow(NoSuchElementException::new);

        review.update(request);
        updateKeywords(request.getKeywordServiceRequests());

        return request.getReviewId();
    }

    public Long delete(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(NoSuchElementException::new);
        reviewRepository.delete(review);
        return reviewId;
    }

    private void updateReviewAchievementCount(ReviewCreateServiceRequest serviceRequest) {
        memberAchievementQueryRepository.searchReviewAchievementByMemberIdAndGenreId(serviceRequest.getMemberId(), serviceRequest.getGenreId());
    }

    /**
     * private methods
     */

    private List<Keyword> createKeywords(Long saveId, List<KeywordCreateServiceRequest> keywordServiceRequests) {
        return keywordServiceRequests.stream()
                .map(request -> request.toEntity(saveId))
                .collect(Collectors.toList());
    }

    private void updateKeywords(List<KeywordUpdateServiceRequest> keywordRequests) {
        keywordRequests.forEach(keywordRequest ->
                keywordRepository.findById(keywordRequest.getKeywordId())
                        .orElseThrow(NoSuchElementException::new)
                        .update(keywordRequest.getName(), keywordRequest.getWeight())
        );
    }

    private void createBackgroundImage(Long reviewId, UploadFile uploadFile) {
        BackgroundImage backgroundImage = BackgroundImage.builder()
                .review(Review.builder().id(reviewId).build())
                .uploadFile(uploadFile)
                .build();
        backgroundImageRepository.save(backgroundImage);
    }

    private List<Notification> createNotifications(ReviewCreateServiceRequest request, Long saveId) {
        List<Long> memberIds = interestQueryRepository.searchRandomMember(request.getGenreId(), MEMBER_LIMIT_COUNT);
        if (memberIds.isEmpty()) {
            return new ArrayList<>();
        }
        return memberIds.stream().map(memberId -> Notification.builder()
                .review(Review.builder().id(saveId).build())
                .member(Member.builder().id(memberId).build())
                .build()).collect(Collectors.toList());
    }


}
