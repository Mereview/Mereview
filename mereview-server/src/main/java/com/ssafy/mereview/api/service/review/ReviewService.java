package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.review.dto.request.KeywordCreateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.request.ReviewCreateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.request.ReviewUpdateServiceRequest;
import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.review.entity.BackgroundImage;
import com.ssafy.mereview.domain.review.entity.Keyword;
import com.ssafy.mereview.domain.review.entity.Review;
import com.ssafy.mereview.domain.review.repository.BackgroundImageRepository;
import com.ssafy.mereview.domain.review.repository.KeywordRepository;
import com.ssafy.mereview.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Long create(ReviewCreateServiceRequest request) {
        Long saveId = reviewRepository.save(request.toEntity()).getId();

        keywordRepository.saveAll(createKeywords(saveId, request.getKeywordServiceRequests()));
        backgroundImageRepository.save(createBackgroundImage(saveId, request.getUploadFile()));

        return saveId;
    }

    public Long update(ReviewUpdateServiceRequest request) {
        Review review = reviewRepository.findById(request.getReviewId())
                .orElseThrow(NoSuchElementException::new);

        review.update(request);

        return request.getReviewId();
    }

    public Long delete(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(NoSuchElementException::new);
        reviewRepository.delete(review);
        return reviewId;
    }

    /**
     * private methods
     */

    private List<Keyword> createKeywords(Long saveId, List<KeywordCreateServiceRequest> keywordServiceRequests) {
        return keywordServiceRequests.stream()
                .map(request -> request.toEntity(saveId))
                .collect(Collectors.toList());
    }

    private BackgroundImage createBackgroundImage(Long reviewId, UploadFile uploadFile) {
        return BackgroundImage.builder()
                .review(Review.builder().id(reviewId).build())
                .uploadFile(uploadFile)
                .build();
    }
}
