package com.erser.jpashop.controller;

import com.erser.jpashop.dto.MemberDto;
import com.erser.jpashop.entity.Member;
import com.erser.jpashop.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {
    @Autowired
    private MemberService memberService;

    @Autowired // 모의 객체
    private MockMvc mockMvc;

    @Autowired  // 암호화
    private PasswordEncoder passwordEncoder;

    public Member createMember(String email, String password){
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail(email);
        memberDto.setPassword(password);
        memberDto.setName("홍길동");
        memberDto.setAddress("부산");
        // dto -> entity
        Member member = Member.createMember(memberDto, passwordEncoder);
        return memberService.saveMember(member);
    }

    @Test
    void loginTest() throws Exception {
        Member member = createMember("test@test.com", "1234");
        mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin()
                        .loginProcessingUrl("/members/login")
                .userParameter("email")
                .user("test@test.com")
                .password("1234"))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }
}