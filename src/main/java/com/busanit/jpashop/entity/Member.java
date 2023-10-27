package com.busanit.jpashop.entity;

import com.busanit.jpashop.constant.Role;
import com.busanit.jpashop.dto.MemberDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter @Setter @ToString
public class Member extends BaseTimeEntity {
    @Id @Column(name = "member_id")
    @GeneratedValue
    private Long id;
    @Column
    private String name;

    // 유일한 값이 들어갈 수 있게 속성 지정. 로그인 시 사용
    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    // 열거형 타입, 순서가 바뀌어도 내용이 변경되지 않게 String 타입
    @Enumerated(EnumType.STRING)
    private Role role;


    // 생성 메서드 dto -> 엔티티
    public static Member createMember(MemberDto memberDto,
                                      PasswordEncoder passwordEncoder) {
        Member member = new Member();

        member.setName(memberDto.getName());
        member.setEmail(memberDto.getEmail());
        member.setAddress(memberDto.getAddress());
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(memberDto.getPassword());
        member.setPassword(encodedPassword);

        member.setRole(Role.ADMIN);

        return member;
    }
}
