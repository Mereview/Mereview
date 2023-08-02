package com.ssafy.mereview.api.service.review.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BackgroundImageResponse {
    private Long id;
    private String fileName;
    private LocalDateTime createdTime;

    @Builder
    public BackgroundImageResponse(Long id, String fileName, LocalDateTime createdTime) {
        this.id = id;
        this.fileName = fileName;
        this.createdTime = createdTime;
    }
}
