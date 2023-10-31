package com.busanit.jpashop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 업로드한 파일을 읽어올 경로를 설정
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // 설정 정보에서 로컬 파일 위치 -> ㄴfile:///C:/shop/
    @Value("${uploadPath}")
    String uploadPath;

    // URL 경로를 /images/.... -> file:///C:/shop/...
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations(uploadPath);
    }
}
