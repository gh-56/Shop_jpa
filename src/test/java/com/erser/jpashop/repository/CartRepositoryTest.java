package com.erser.jpashop.repository;

import com.erser.jpashop.dto.MemberDto;
import com.erser.jpashop.entity.Cart;
import com.erser.jpashop.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CartRepositoryTest {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;

    @Test
    void 장바구니회원테스트() {
        // given 회원 생성
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail("tester1@test.com");
        memberDto.setName("홍길동");
        memberDto.setAddress("부산시 남구 남천동");
        memberDto.setPassword("1234");
        Member member = Member.createMember(memberDto, passwordEncoder);
        memberRepository.save(member);

        // 장바구니 생성
        Cart cart = new Cart();
        cart.setMember(member);
        cartRepository.save(cart);

        // 영속성 객체 저장 및 비우기
        em.flush();     // Transactional과 상관없이 sql문 강제 호출
        em.clear();     // 영속성 객체 비우기

        // when
        Cart findCart = cartRepository.findById(cart.getCartId()).orElse(null);

        // then
        Assertions.assertThat(findCart.getCartId()).isEqualTo(member.getId());
    }
}