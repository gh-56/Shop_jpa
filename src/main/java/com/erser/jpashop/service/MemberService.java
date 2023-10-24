package com.erser.jpashop.service;

import com.erser.jpashop.entity.Member;
import com.erser.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor        // 생성자 의존성 주입
public class MemberService implements UserDetailsService {
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
            throw new IllegalStateException("가입된 회원입니다.");
        }
    }

    // 회원 서비스 계층에서 스프링 사용자 정보 구현

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // DB에서 회원 정보 조회(e-mail)
        Member member = memberRepository.findByEmail(email);

        // 이메일에 해당하는 사용자가 없을 경우 예외발생
        if (member == null){
            throw new UsernameNotFoundException(email);
        }
        // 스프링 시큐리티 사용자 객체를 반환
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

}
