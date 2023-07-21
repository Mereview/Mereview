package com.ssafy.mereview.domain.member.service;

import com.ssafy.mereview.domain.member.service.dto.SaveMemberDto;

public interface MemberService {

    Long saveMember(SaveMemberDto dto);
}
