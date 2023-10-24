package com.erser.jpashop.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("ADMIN 권한으로 상품등록 페이지 접근 했을 때 => 200")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void test() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/item/new")) // 상품등록 페이지
                .andDo(print()) // 로그 출력
                .andExpect(MockMvcResultMatchers.status().isOk()); // 검증. 200이 맞는지
    }

    @Test
    @DisplayName("USER 권한으로 상품등록 페이지 접근 했을 때 => 403")
    @WithMockUser(username = "admin", roles = "USER")
    void test2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/item/new")) // 상품등록 페이지
                .andDo(print()) // 로그 출력
                .andExpect(MockMvcResultMatchers.status().isForbidden()); // 검증. Forbidden이 맞는지
    }

    @Test
    @DisplayName("권한 없이 상품등록 페이지 접근 했을 때 => 302")
    void test3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/item/new")) // 상품등록 페이지
                .andDo(print()) // 로그 출력
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection()); // 검증
    }
}