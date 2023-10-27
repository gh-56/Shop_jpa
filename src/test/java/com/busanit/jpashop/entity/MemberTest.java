package com.busanit.jpashop.entity;

import com.busanit.jpashop.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @WithMockUser(username = "honggildong", roles = "USER")
    void audit_테스트() {
        Member member = new Member();
        memberRepository.save(member);

        em.flush();
        em.clear();

        Member findMember = memberRepository.findById(member.getId()).orElse(null);

        System.out.println("등록시간 :" +member.getRegTime());
        System.out.println("수정시간 :" +member.getUpdateTime());
//        System.out.println("등록자 :" +member.getCreatedBy());
//        System.out.println("수정자 :" +member.getModifiedBy());


    }
}