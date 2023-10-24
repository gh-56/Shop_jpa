package com.busanit.jpashop.config;

import com.busanit.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity  // 웹 보안
@Configuration      // 설정 정보 컴포넌트 등록 선언
@RequiredArgsConstructor
public class SecurityConfig {

    // 스프링 컨테이너 의존성 주입
    private final MemberService memberService;
    
    // http 요청에 대한 보안 설정
    @Bean       // 스프링 컨테이너 등록
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 로그인 관련 설정
        http.formLogin(
                form -> form
                        .loginPage("/members/login")  // 기본 로그인 페이지 URL을 설정
                        .defaultSuccessUrl("/")      // 로그인에 성공했을 때 URL
                        .usernameParameter("email")  // 로그인에 사용할 매개변수 username -> email
                        .failureUrl("/members/login/error") // 실패했을 때 보낼 URL
        );

//        http.authorizeHttpRequests(auth ->
//                auth.
//        )


        // CSRF 토큰 검증 무효화
        // http.csrf().disable();
        return http.build();
    }

    // 해시 함수를 이용한 비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
