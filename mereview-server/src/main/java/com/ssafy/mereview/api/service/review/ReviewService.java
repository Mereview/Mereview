package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.review.dto.request.KeywordCreateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.request.ReviewCreateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.response.ReviewResponse;
import com.ssafy.mereview.domain.movie.entity.MovieGenre;
import com.ssafy.mereview.domain.review.entity.*;
import com.ssafy.mereview.domain.review.repository.BackgroundImageRepository;
import com.ssafy.mereview.domain.review.repository.KeywordRepository;
import com.ssafy.mereview.domain.review.repository.ReviewQueryRepository;
import com.ssafy.mereview.domain.review.repository.ReviewRepository;
import com.ssafy.mereview.domain.review.repository.dto.SearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.mereview.domain.review.entity.ReviewLikeType.*;

@RequiredArgsConstructor
@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;
    private final KeywordRepository keywordRepository;
    private final BackgroundImageRepository backgroundImageRepository;

    public Long createReview(ReviewCreateServiceRequest request) {
        Review saveReview = request.toEntity();
        Long saveId = reviewRepository.save(saveReview).getId();

        keywordRepository.saveAll(createKeywords(saveId, request.getKeywordServiceRequests()));
        backgroundImageRepository.save(createBackgroundImage(request, saveId));

        return saveId;
    }

    private List<Keyword> createKeywords(Long saveId, List<KeywordCreateServiceRequest> keywordServiceRequests) {
        return keywordServiceRequests.stream()
                .map(request -> request.toEntity(saveId))
                .collect(Collectors.toList());
    }

    private static BackgroundImage createBackgroundImage(ReviewCreateServiceRequest request, Long saveId) {
        return BackgroundImage.builder()
                .review(Review.builder().id(saveId).build())
                .uploadFile(request.getUploadFile())
                .build();
    }
}
