package com.ssafy.mereview.api.service.review.dto.request;

import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.review.entity.MovieRecommendType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewUpdateServiceRequest {

    private Long reviewId;
    private String title;
    private String content;
    private String highlight;
    private MovieRecommendType type;
    private List<KeywordUpdateServiceRequest> keywordServiceRequests;
    private Boolean updateBackGround;
    private UploadFile uploadFile;

    @Builder
    public ReviewUpdateServiceRequest(Long reviewId, String title, String content, String highlight, MovieRecommendType type, List<KeywordUpdateServiceRequest> keywordServiceRequests, Boolean updateBackGround, UploadFile uploadFile) {
        this.reviewId = reviewId;
        this.title = title;
        this.content = content;
        this.highlight = highlight;
        this.type = type;
        this.keywordServiceRequests = keywordServiceRequests;
        this.updateBackGround = updateBackGround;
        this.uploadFile = uploadFile;
    }
}
