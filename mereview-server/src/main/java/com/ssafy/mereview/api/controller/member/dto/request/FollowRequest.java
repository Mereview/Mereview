package com.ssafy.mereview.api.controller.member.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FollowRequest {

    private Long memberId;

    private Long targetId;
}
