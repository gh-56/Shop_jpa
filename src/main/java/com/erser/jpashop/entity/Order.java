package com.erser.jpashop.entity;


import com.erser.jpashop.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** 주문 | orders */

@Entity
@Table(name = "orders") // SQL문의 order by 예약어로 order 사용 불가
@Getter @Setter
public class Order extends BaseEntity{

    // 주문id
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // 주문일자
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    // 주문상태
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    // 다대일 관계 : 한 명의 회원은 여러번 주문할 수 있다.
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 일대다 관계
    // 외래키를 가지고 있는 연관관계의 주인 엔티티를 참조하는 목록을 필드로 갖는다 (연관관계의 주인이 아님)
    // 연관관계의 주인을 mappedBy로 설정
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    // 새로운 주문 생성
    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setMember(member); // 회원 세팅
        for (OrderItem orderItem : orderItemList) { // 주문상품 목록 세팅
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.ORDER);    // 주문상태 ORDER 세팅
        order.setOrderDate(LocalDateTime.now());    // 주문시간 세팅
        return order;
    }

    private void addOrderItem(OrderItem orderItem) {
        // 양방향 참조 관계
        orderItems.add(orderItem);
        orderItem.setOrders(this);
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    // 일대다 : 하나의 주문이 여러개의 주문 상품을 가지므로 List 자료형으로 매핑
}

