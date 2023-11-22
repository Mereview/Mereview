package com.ssafy.mereview.api.service.member;

import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.repository.MemberQueryRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberQueryRepository memberRepository;
    public UserDetailsServiceImpl(MemberQueryRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.searchByEmail(email);

        if (member == null) {
            return null;
        }

        return User.withUsername(member.getEmail())
                .password(member.getPassword())
                .authorities(new ArrayList<>()) // 여기에 필요한 경우 권한을 부여할 수 있습니다.
                .build();
    }

}
