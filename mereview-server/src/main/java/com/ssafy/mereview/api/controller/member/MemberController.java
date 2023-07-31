package com.ssafy.mereview.api.controller.member;

import com.ssafy.mereview.api.controller.member.dto.request.InterestRequest;
import com.ssafy.mereview.api.controller.member.dto.request.MemberLoginRequest;
import com.ssafy.mereview.api.controller.member.dto.request.MemberRegisterRequest;
import com.ssafy.mereview.api.service.member.MemberService;
import com.ssafy.mereview.api.service.member.dto.request.SaveMemberReqeust;
import com.ssafy.mereview.api.service.member.dto.response.MemberResponse;
import com.ssafy.mereview.common.util.jwt.JwtUtils;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.repository.MemberQueryRepository;
import com.ssafy.mereview.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public Long signup(@Valid @RequestBody MemberRegisterRequest dto) throws Exception {
        List<InterestRequest> interestRequests = new ArrayList<>();
        for (String genreName : dto.getInterests()) {
            InterestRequest genreRequest = InterestRequest.builder()
                    .genreName(genreName)
                    .build();
            interestRequests.add(genreRequest);
        }

        SaveMemberReqeust saveMemberReqeust = SaveMemberReqeust.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .interestRequests(interestRequests)
                .build();
        Long id = memberService.saveMember(saveMemberReqeust);
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
