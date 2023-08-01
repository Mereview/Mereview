package com.ssafy.mereview.api.controller.member;

import com.ssafy.mereview.api.controller.member.dto.request.MemberLoginRequest;
import com.ssafy.mereview.api.controller.member.dto.request.MemberRegisterRequest;
import com.ssafy.mereview.api.service.member.MemberService;
import com.ssafy.mereview.api.service.member.dto.request.SaveMemberServiceRequest;
import com.ssafy.mereview.api.service.member.dto.response.MemberLoginResponse;
import com.ssafy.mereview.api.service.member.dto.response.MemberResponse;
import com.ssafy.mereview.common.response.ApiResponse;
import com.ssafy.mereview.common.util.jwt.JwtUtils;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.repository.MemberQueryRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {


    private final MemberService memberService;



    @PostMapping("/sign-up")
    public ApiResponse<Long> signup(@Valid @RequestBody MemberRegisterRequest request) throws Exception {
        log.debug("MemberRegisterRequest : {}", request);
        SaveMemberServiceRequest saveMemberServiceRequest = request.toServiceDto();

        Long memberId = memberService.createMember(saveMemberServiceRequest);
        if (memberId == -1) {
            throw new DuplicateRequestException("중복되는 회원이 존재합니다.");
        }

        return ApiResponse.ok(memberId);
    }

    @PostMapping("/login")
    public ApiResponse<MemberLoginResponse> login(@RequestBody @Valid MemberLoginRequest request) {
        log.debug("MemberLoginRequest : {}", request);
        MemberLoginResponse memberLoginResponse = memberService.login(request);
        return ApiResponse.ok(memberLoginResponse);

    }

    @GetMapping("/{id}")
    public ApiResponse<MemberResponse> searchMemberInfo(@PathVariable Long id) {
        log.debug("MemberController.getMemberInfo : {}", id);
        MemberResponse memberResponse = memberService.searchMemberInfo(id);
        return ApiResponse.ok(memberResponse);
    }

    @PostMapping("/follow/{targetId}")
    public void follow(@PathVariable Long targetId, @RequestBody Long currentUserId) {
        log.debug("MemberController.follow : {}", targetId);
        memberService.follow(targetId, currentUserId);
    }
}
