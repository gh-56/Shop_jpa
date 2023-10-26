package com.busanit.jpashop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class CartItem {

    @Id @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    // 외래키, 단방향 다대일 관계
    // 하나의 장바구니에는 여러 개의 상품을 담을 수 있다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    // 하나의 상품은 여러 장바구니의 장바구니 상품으로 담길 수 있다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;      // 장바구니에 담을 같은 상품 개수
}
