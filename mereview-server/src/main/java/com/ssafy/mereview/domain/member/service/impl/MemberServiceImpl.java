package com.ssafy.mereview.domain.member.service.impl;

import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.entity.Role;
import com.ssafy.mereview.domain.member.repository.MemberQueryRepository;
import com.ssafy.mereview.domain.member.repository.MemberRepository;
import com.ssafy.mereview.domain.member.repository.impl.MemberQueryRepositoryImpl;
import com.ssafy.mereview.domain.member.service.MemberService;
import com.ssafy.mereview.domain.member.service.dto.SaveMemberDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    private final MemberQueryRepository memberQueryRepository;

    @Override
    public Long saveMember(SaveMemberDto dto) {

        if (memberQueryRepository.searchByEmail(dto.getEmail()) != null) {
            return -1L;
        } else {
            Member member = Member.builder()
                    .email(dto.getEmail())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .build();
            memberRepository.save(member);
            memberQueryRepository.searchAllGenre();

            Long id = member.getId();
            return id;
        }


    }

}
