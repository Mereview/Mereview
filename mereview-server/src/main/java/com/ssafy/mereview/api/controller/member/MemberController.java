package com.ssafy.mereview.api.controller.member;

import com.ssafy.mereview.api.controller.member.dto.request.MemberLoginRequest;
import com.ssafy.mereview.api.controller.member.dto.request.MemberRegisterRequest;
import com.ssafy.mereview.api.service.member.MemberService;
import com.ssafy.mereview.api.service.member.dto.request.SaveMemberServiceRequest;
import com.ssafy.mereview.api.service.member.dto.response.MemberResponse;
import com.ssafy.mereview.common.util.jwt.JwtUtils;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.repository.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberQueryRepository memberQueryRepository;


    private final MemberService memberService;

    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;


    @PostMapping("/sign-up")
    public Long signup(@Valid @RequestBody MemberRegisterRequest request) throws Exception {


        SaveMemberServiceRequest saveMemberServiceRequest = request.toServiceDto();
        Long id = memberService.saveMember(saveMemberServiceRequest);
        if (id == -1) {
            throw new Exception("중복되는 회원이 존재합니다.");
        }


        return id;


    }

    @ResponseBody
    @PostMapping("/login")
    public MemberResponse login(@RequestBody MemberLoginRequest memberLoginDto) {
        // TODO: Verify username and password, and generate JWT
        Member existingMember = memberQueryRepository.searchByEmail(memberLoginDto.getEmail());
        if (existingMember != null && passwordEncoder.matches(memberLoginDto.getPassword(), existingMember.getPassword())) {
            Map<String, String> token = jwtUtils.generateJwt(existingMember);
            MemberResponse memberResponse = memberService.getLoginInfo(existingMember.getId());
            memberResponse.setToken(token);
            return memberResponse;
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

    @ResponseBody
    @GetMapping("/{id}")
    public MemberResponse getMemberInfo(@PathVariable Long id) {
        System.out.println("id = " + id);
        MemberResponse memberResponse = memberService.getMemberInfo(id);
        return memberResponse;
    }

    @ResponseBody
    @PostMapping("/follow/{targetId}")
    public void follow(@PathVariable Long targetId) {
        memberService.follow(targetId, 1L);
    }
}
