package com.erser.jpashop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity  // 웹 보안
@Configuration      // 설정 정보 컴포넌트 등록 선언
public class SecurityConfig {
    // http 요청에 대한 보안 설정
    @Bean   // 스프링 컨테이너 등록
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.build();
    }

    // 해시 함수를 이용한 비밀번호 암호
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
