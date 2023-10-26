package com.busanit.jpashop.repository;

import com.busanit.jpashop.dto.MemberDto;
import com.busanit.jpashop.entity.Cart;
import com.busanit.jpashop.entity.Member;
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

    @Autowired CartRepository cartRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;

    @Test
    void 장바구니회원테스트() {
        // given 회원생성
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail("test@test.com");
        memberDto.setName("홍길동");
        memberDto.setAddress("부산시 남구 남천동");
        memberDto.setPassword("1234");
        Member member = Member.createMember(memberDto, passwordEncoder);
        memberRepository.save(member);  // 회원 저장

        // 장바구니 생성
        Cart cart = new Cart();
        cart.setMember(member);
        cartRepository.save(cart);

        // 영속성 객체 저장 및 비우기
        em.flush();     // 트랜잭셔널과 상관없이 sql문 강제 호출
        em.clear();     // 영속성 객체 비우기

        // when : 장바구니 DB 조회를 저장한 id 기준으로 했을 때  
        Cart findCart = cartRepository.findById(cart.getId()).orElse(null);
        
        // then : 조회 결과의 멤버 id가 생성한 id와 일치하는지 검증
        Assertions.assertThat(findCart.getId()).isEqualTo(member.getId());

    }
}