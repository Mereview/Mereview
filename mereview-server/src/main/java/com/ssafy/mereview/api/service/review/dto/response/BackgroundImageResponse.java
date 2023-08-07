package com.ssafy.mereview.api.service.review.dto.response;

import com.ssafy.mereview.domain.review.entity.BackgroundImage;
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

    public static BackgroundImageResponse of(BackgroundImage backgroundImage) {
        return BackgroundImageResponse.builder()
                .id(backgroundImage.getId())
                .fileName(backgroundImage.getUploadFile().getUploadFileName())
                .createdTime(backgroundImage.getCreatedTime())
                .build();
    }
}
