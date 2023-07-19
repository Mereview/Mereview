package com.ssafy.mereview.domain.member.entity;

import com.ssafy.mereview.common.util.JwtUtils;
import com.ssafy.mereview.domain.member.controller.MemberController;
import com.ssafy.mereview.domain.member.controller.dto.req.MemberLoginDto;
import com.ssafy.mereview.domain.member.controller.dto.req.MemberRegisterDto;
import com.ssafy.mereview.domain.member.repository.MemberQueryRepository;
import com.ssafy.mereview.domain.member.repository.MemberRepository;
import com.ssafy.mereview.domain.member.service.MemberService;
import com.ssafy.mereview.domain.member.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback(false)
class MemberAuthTest {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberQueryRepository memberQueryRepository;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new MemberController(memberService, memberRepository, memberQueryRepository, jwtUtils, passwordEncoder)).build();
    }


    @Test
    public void signUpTest() throws Exception {
        //Given
        MemberRegisterDto memberRegisterDto = new MemberRegisterDto();
        memberRegisterDto.setEmail("test@example.com");
        memberRegisterDto.setPassword("password123");

        Member savedMember = new Member("test@example.com", "encryptedPassword", "test", null);
        when(passwordEncoder.encode("password123")).thenReturn("encryptedPassword");
        when(memberRepository.save(any(Member.class))).thenReturn(savedMember);

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\",\"password\":\"password123\"}"))

                // Then
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(passwordEncoder).encode("password123");
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    public void loginTest() throws Exception {
        // Given
        MemberLoginDto memberLoginDto = new MemberLoginDto();
        memberLoginDto.setEmail("test@example.com");
        memberLoginDto.setPassword("password123");

        Member savedMember = new Member("test@example.com", "encryptedPassword", "test", null);
        when(memberQueryRepository.searchByEmail("test@example.com")).thenReturn(savedMember);
        when(passwordEncoder.matches("password123", savedMember.getPassword())).thenReturn(true);

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\",\"password\":\"password123\"}"))

                // Then
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify
        verify(memberQueryRepository).searchByEmail("test@example.com");
        verify(passwordEncoder).matches("password123", savedMember.getPassword());
    }
}