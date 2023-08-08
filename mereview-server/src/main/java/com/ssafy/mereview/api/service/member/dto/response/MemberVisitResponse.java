package com.ssafy.mereview.api.service.member.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberVisitResponse {

    private Long id;

    private int todayVisit;

    private int totalVisit;

}
