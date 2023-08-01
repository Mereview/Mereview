package com.ssafy.mereview.api.service.review.dto.request;

import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.review.entity.EvaluationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewUpdateServiceRequest {

    private String title;
    private String content;
    private String highlight;
    private EvaluationType type;
    private List<KeywordUpdateServiceRequest> keywordServiceRequests;
    private UploadFile uploadFile;

    @Builder
    public ReviewUpdateServiceRequest(String title, String content, String highlight, EvaluationType type, List<KeywordUpdateServiceRequest> keywordServiceRequests, UploadFile uploadFile) {
        this.title = title;
        this.content = content;
        this.highlight = highlight;
        this.type = type;
        this.keywordServiceRequests = keywordServiceRequests;
        this.uploadFile = uploadFile;
    }
}
