package com.busanit.jpashop.controller;

import com.busanit.jpashop.dto.OrderDto;
import com.busanit.jpashop.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/order")
    @ResponseBody
    public ResponseEntity order (@RequestBody @Valid OrderDto orderDto, Principal principal){


        // 스프링 시큐리티(Principal 객체에서 회원 정보 가져오기)
        String email = principal.getName();

        // 서비스 계층에 위임
        orderService.order(orderDto, email);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }


}
