package com.busanit.jpashop.dto;

import com.busanit.jpashop.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// 주문 상품 정보 => 주문이력 페이지
@Getter @Setter
public class OrderItemDto {
    private String itemNm; // 상품명
    private int count; // 주문 수량
    private int orderPrice; // 주문 금액
    private String imgUrl; // 이미지 경로

    // 커스텀 생성자 orderItem, imgUrl => dto
    public OrderItemDto(OrderItem orderItem, String imgUrl) {
        this.itemNm = orderItem.getItem().getItemNm();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl;
    }
}
