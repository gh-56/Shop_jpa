package com.busanit.jpashop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

// 회원 가입시 정보를 전달하는 개체
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class MemberDto {

    // Null 체크, 문자열 길이 0 체크, " " 빈 문자열 체크
    @NotBlank(message = "필수 입력 값입니다.")
    private String name;

    // 값이 e-mail 형식인지 검사
    @Email(message = "이메일 형식으로 입력해주세요.")
    @NotEmpty
    private String email;

    // Null 체크, 문자열 길이 0 체크
    @NotEmpty(message = "필수 입력 값입니다.")
    // 최소 최대 길이 검사
    @Length(min = 4, max = 16)
    private String password;

    // Null 체크
    @NotEmpty(message = "필수 입력 값입니다.")
    private String address;
}
