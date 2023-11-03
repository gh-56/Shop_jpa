package com.busanit.jpashop.dto;

import com.busanit.jpashop.constant.OrderStatus;
import com.busanit.jpashop.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OrderHistDto {

    private Long orderId;  // 주문ID
    private String orderDate;  // 주문날짜
    private OrderStatus orderStatus; // 주문상태
    private List<OrderItemDto> orderItemDtoList = new ArrayList<>(); // 주문상품 목록

    // DTO의 생성자로 엔티티 객체를 받아 세팅
    public OrderHistDto(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
    }

    // 주문 상품 목록 추가 메서드
    public void addOrderItemDto(OrderItemDto orderItemDto) {
        orderItemDtoList.add(orderItemDto);
    }
}
