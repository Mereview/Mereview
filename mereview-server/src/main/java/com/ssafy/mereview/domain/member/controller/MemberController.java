package com.ssafy.mereview.domain.member.controller;

import com.ssafy.mereview.common.util.JwtUtils;
import com.ssafy.mereview.domain.member.controller.dto.req.MemberRegisterDto;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Access;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class MemberController {
    private final MemberRepository memberRepository;
    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;

    public MemberController(MemberRepository memberRepository, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public void signup(@RequestBody MemberRegisterDto memberRegisterDto) {
        // TODO: Add validation and password encryption
        Member member = new Member(memberRegisterDto.getEmail(), passwordEncoder.encode(memberRegisterDto.getPassword()), "test");
        memberRepository.save(member);
    }

    @PostMapping("/login")
    public String login(@RequestBody Member member) {
        // TODO: Verify username and password, and generate JWT
        Member existingMember = memberRepository.findByEmail(member.getEmail());
        if (existingMember != null && passwordEncoder.matches(member.getPassword(), existingMember.getPassword())) {


            return jwtUtils.generateJwt(existingMember);
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
