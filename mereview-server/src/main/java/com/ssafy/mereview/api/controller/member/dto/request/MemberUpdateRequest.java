package com.ssafy.mereview.api.controller.member.dto.request;

import com.ssafy.mereview.api.service.member.dto.request.MemberCreateServiceRequest;
import com.ssafy.mereview.api.service.member.dto.request.MemberUpdateServiceRequest;
import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.member.entity.ProfileImage;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class MemberUpdateRequest {

    Long id;

    private String nickname;

    private String gender;

    private String birthDate;

    List<InterestRequest> interests = new ArrayList<>();

    @Builder
    public MemberUpdateRequest(String nickname, String gender, String birthDate, ProfileImage profileImage, List<InterestRequest> interestRequestss, UploadFile uploadFile) {
        this.nickname = nickname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.interests = interestRequestss;
    }

    public MemberUpdateServiceRequest toMemberCreateServiceRequest() {
        return MemberUpdateServiceRequest.builder()
                .id(id)
                .nickname(nickname)
                .birthDate(birthDate)
                .interestRequests(createInterestRequests())
                .build();
    }

    private List<InterestRequest> createInterestRequests() {
        return this.interests.stream().map(interest -> InterestRequest.builder()
                .genreId(interest.getGenreId())
                .genreName(interest.getGenreName())
                .build()).collect(Collectors.toList());
    }
}
