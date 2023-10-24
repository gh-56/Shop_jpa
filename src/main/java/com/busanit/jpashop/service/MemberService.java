package com.busanit.jpashop.service;

import com.busanit.jpashop.entity.Member;
import com.busanit.jpashop.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor    // 생성자 의존성 주입
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    // 회원가입
    public Member saveMember(Member member) {
        // 중복 회원 검증
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        // 가입을 위해 입력한 멤버가 존재한다면
        if (findMember != null) {
            // 예외 발생
            throw new IllegalStateException("가입된 회원입니다.");
        }
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // DB에서 회원 정보 조회 (e-mail)
        Member member = memberRepository.findByEmail(email);

        // 이메일에 해당하는 사용자가 없을 경우 예외발생
        if (member == null) {
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
