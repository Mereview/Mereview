package com.ssafy.mereview.api.service.member.dto.response;

import com.ssafy.mereview.domain.member.entity.ProfileImage;
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

    public static ProfileImageResponse of(ProfileImage profileImage) {
        return ProfileImageResponse.builder()
                .id(profileImage.getId())
                .fileName(profileImage.getUploadFile().getUploadFileName())
                .createdTime(profileImage.getCreatedTime())
                .build();
    }
}
