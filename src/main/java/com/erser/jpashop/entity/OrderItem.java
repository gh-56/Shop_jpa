package com.erser.jpashop.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/** 주문상품 | order_item */

@Entity
@Table(name = "order_item")
@Getter @Setter
public class OrderItem extends BaseEntity{

    // 주문상품id
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    // 상품가격
    @Column(name = "order_price")
    private Integer orderPrice;

    // 개수
    @Column(name = "count")
    private Integer count;

    // 다대일 연관관계 매핑 : 하나의 주문은 여러 주문상품을 가질 수 있다.
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order orders;

    // 다대일 : 하나의 상품은 여러 주문 상품으로 추가될 수 있다.
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

}

