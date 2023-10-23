package com.erser.jpashop.service;

import com.erser.jpashop.dto.MemberDto;
import com.erser.jpashop.entity.Member;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional  // 해당 클래스 내의 메소드는 테스트 실행 후 롤백 처리
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    static PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원 가입 테스트")
    void saveMember(){
        //given
        Member member = createMember();
        // when
        Member savedMember = memberService.saveMember(member);

        // then
        assertThat(savedMember.getEmail()).isEqualTo(member.getEmail());
        assertThat(savedMember.getRole()).isEqualTo(member.getRole());
    }

    private static Member createMember() {
        MemberDto memberDto = MemberDto.builder()
                .email("test@test.com")
                .name("홍길동")
                .address("부산시")
                .password("1234")
                .build();
        Member member = Member.createMember(memberDto, passwordEncoder);
        return member;
    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    void test2(){
        // given
        Member member1 = createMember();
        Member member2 = createMember();
        memberService.saveMember(member1);

        // when
        Throwable e = assertThrows(IllegalStateException.class, () -> {
            memberService.saveMember(member2);
        });
        // then

    }
}
