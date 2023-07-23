package com.ssafy.mereview.domain.member.controller;

import com.ssafy.mereview.common.util.JwtUtils;
import com.ssafy.mereview.domain.member.controller.dto.req.MemberLoginRequest;
import com.ssafy.mereview.domain.member.controller.dto.req.MemberRegisterRequest;
import com.ssafy.mereview.domain.member.controller.dto.res.MemberResponse;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.repository.MemberQueryRepository;
import com.ssafy.mereview.domain.member.repository.MemberRepository;
import com.ssafy.mereview.domain.member.service.MemberService;
import com.ssafy.mereview.domain.member.service.dto.SaveMemberDto;
import com.ssafy.mereview.domain.movie.entity.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class MemberController {
    private final MemberQueryRepository memberQueryRepository;

    private final MemberRepository memberRepository;

    private final MemberService memberService;

    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;


    @PostMapping("/signup")
    public Long signup(@RequestBody MemberRegisterRequest dto) throws Exception {
        List<Genre> genres = new ArrayList<>();
        for (Long id : dto.getGenres()) {
            genres.add(Genre.builder().genreId(id).build());
        }

        SaveMemberDto saveMemberDto = SaveMemberDto.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .genres(genres)
                .build();
        Long id = memberService.saveMember(saveMemberDto);
        if (id == -1) {
            throw new Exception("중복되는 회원이 존재합니다.");
        }


        return id;


    }

    @ResponseBody
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody MemberLoginRequest memberLoginDto) {
        // TODO: Verify username and password, and generate JWT
        Member existingMember = memberQueryRepository.searchByEmail(memberLoginDto.getEmail());
        if (existingMember != null && passwordEncoder.matches(memberLoginDto.getPassword(), existingMember.getPassword())) {
            Map<String, String> token = jwtUtils.generateJwt(existingMember);

            return token;
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

    @ResponseBody
    @GetMapping("/info/{id}")
    public MemberResponse getMemberInfo(@PathVariable Long id) {
        System.out.println("id = " + id);
        MemberResponse response = memberService.getMemberInfo(id);
        return response;
    }
}
