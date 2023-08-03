package com.ssafy.mereview.api.controller.member.dto.request;

import com.ssafy.mereview.api.service.member.dto.request.MemberUpdateServiceRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class MemberUpdateRequest {

    private String nickname;

    private String gender;

    private String birthDate;

    List<InterestRequest> interests = new ArrayList<>();

    @Builder
    public MemberUpdateRequest(String nickname, String gender, String birthDate, List<InterestRequest> interestRequests) {
        this.nickname = nickname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.interests = interestRequests;
    }

    public MemberUpdateServiceRequest toMemberCreateServiceRequest() {
        return MemberUpdateServiceRequest.builder()
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
