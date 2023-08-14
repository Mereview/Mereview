package com.ssafy.mereview.api.controller.member.dto.request;

import com.ssafy.mereview.api.service.member.dto.request.MemberNicknameUpdateServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberNicknameUpdateRequest {

        Long id;
        String nickname;

        @Builder
        public MemberNicknameUpdateRequest(Long id, String nickname) {
            this.id = id;
            this.nickname = nickname;
        }

        public MemberNicknameUpdateServiceRequest toServiceRequest() {
            return MemberNicknameUpdateServiceRequest.builder()
                    .id(id)
                    .nickname(nickname)
                    .build();
        }
}
