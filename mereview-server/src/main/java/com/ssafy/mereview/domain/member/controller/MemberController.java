package com.ssafy.mereview.domain.member.controller;

import com.ssafy.mereview.common.util.JwtUtils;
import com.ssafy.mereview.domain.member.controller.dto.req.MemberLoginDto;
import com.ssafy.mereview.domain.member.controller.dto.req.MemberRegisterDto;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.repository.MemberQueryRepository;
import com.ssafy.mereview.domain.member.repository.MemberRepository;
import com.ssafy.mereview.domain.member.service.MemberService;
import com.ssafy.mereview.domain.member.service.dto.SaveMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class MemberController {
    private final MemberQueryRepository memberQueryRepository;

    private final MemberRepository memberRepository;

    private final MemberService memberService;

    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;

    public MemberController(MemberService memberService, MemberRepository memberRepository, MemberQueryRepository memberQueryRepository, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.memberQueryRepository = memberQueryRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public Long signup(@RequestBody MemberRegisterDto memberRegisterDto) throws Exception {
        try {
            SaveMemberDto saveMemberDto = SaveMemberDto.builder()
                    .email(memberRegisterDto.getEmail())
                    .password(memberRegisterDto.getPassword())
                    .build();
            Long id = memberService.saveMember(saveMemberDto);
            if (id == -1) {
                throw new Exception("중복되는 회원이 존재합니다.");
            }
            return id;

        } catch (Exception e) {
            throw new Exception("회원가입에 실패하였습니다.");
        }


    }

    @ResponseBody
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody MemberLoginDto memberLoginDto) {
        // TODO: Verify username and password, and generate JWT
        Member existingMember = memberQueryRepository.searchByEmail(memberLoginDto.getEmail());
        if (existingMember != null && passwordEncoder.matches(memberLoginDto.getPassword(), existingMember.getPassword())) {
            Map<String, String> token = jwtUtils.generateJwt(existingMember);

            return token;
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
