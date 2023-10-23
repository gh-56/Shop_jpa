package com.erser.jpashop.dto;

import lombok.*;

// 회원 가입시 정보를 전달하는 개체
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class MemberDto {
    private String name;
    private String email;
    private String password;
    private String address;
}
