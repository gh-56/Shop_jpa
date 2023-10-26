package com.busanit.jpashop.entity;

import com.busanit.jpashop.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


/** 주문 | orders */

@Entity
@Table(name = "orders")  // SQL 문 order by 예약어로 order 사용 불가
@Getter @Setter
public class Order {

    // 주문 id
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // 주문일자
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    // 주문상태
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    // 다대일 관계 : 한명의 회원은 여러 번 주문할 수 있다.
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}

