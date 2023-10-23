package com.erser.jpashop.service;

import com.erser.jpashop.entity.Member;
import com.erser.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor        // 생성자 의존성 주입
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원가입
    public Member saveMember(Member member){
        // 중복 회원 확인
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null){
            throw new IllegalArgumentException("가입된 회원입니다.");
        }
    }
}
