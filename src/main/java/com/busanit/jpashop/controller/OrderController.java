package com.busanit.jpashop.controller;

import com.busanit.jpashop.dto.OrderDto;
import com.busanit.jpashop.dto.OrderHistDto;
import com.busanit.jpashop.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    // CREATE : 주문 생성
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

    // READ : 주문 조회
    @GetMapping(value = {"/orders", "/orders/{page}"})
    public String orderHist(Model model, @PathVariable("page") Optional<Integer> page, Principal principal) {

        // 페이지 객체
        Pageable pageable =  PageRequest.of(page.isPresent() ? page.get() : 0, 4);

        // 비즈니스 로직 서비스 계층 위임, 매개변수 : email, page
        // dto 목록 반환
        Page<OrderHistDto> orderHistDtoList = orderService.getOrderList(principal.getName(), pageable);

        // 모델에 주문내용을 담아 뷰에 전달
        model.addAttribute("orders", orderHistDtoList);

        // 페이지 정보 모델에 담아 뷰 전달
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);

        return "order/orderHist";
    }

    // 주문 취소
    @PostMapping("/order/{orderId}/cancel")
    @ResponseBody
    public ResponseEntity cancelOrder(@PathVariable("orderId") Long orderId) {
        // 주문 취소 로직 호출
        orderService.cancelOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderId);
    }

}
