package com.busanit.jpashop.controller;

import com.busanit.jpashop.dto.OrderDto;
import com.busanit.jpashop.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/order")
    @ResponseBody
    public ResponseEntity order (@RequestBody @Valid OrderDto orderDto, Principal principal, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // 예외처리 1 : 유효성 검증 통과하지 못할 경우
            StringBuilder stringBuilder = new StringBuilder();
            // 모든 필드 에러들을 문자열로 연결하여 바디로 반환 (문자열)
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                stringBuilder.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<>(stringBuilder.toString(), HttpStatus.BAD_REQUEST);
        }

        // 스프링 시큐리티(Principal 객체에서 회원 정보 가져오기)
        String email = principal.getName();

        // 예외처리 2
        // 서비스 계층에 위임 도중 예외가 발생한 경우 => 400 상태 에러
        Long orderId;
        try {
            orderId = orderService.order(orderDto, email);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

}
