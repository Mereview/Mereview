package com.ssafy.mereview.api.service.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProfileImageResponse {

    private Long id;
    private String fileName;
    private LocalDateTime createdTime;

    @Builder
    public ProfileImageResponse(Long id, String fileName, LocalDateTime createdTime) {
        this.id = id;
        this.fileName = fileName;
        this.createdTime = createdTime;
    }
}
