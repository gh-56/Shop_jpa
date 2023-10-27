package com.busanit.jpashop.entity;

import com.busanit.jpashop.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/** 주문 | orders */

@Entity
@Table(name = "orders")  // SQL 문 order by 예약어로 order 사용 불가
@Getter @Setter
public class Order extends BaseEntity {

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 일대다 관계 (다대일 관계의 양방향)
    // 외래키를 가지고 있는 연관관계의 주인 엔티티를 참조하는 목록을 필드로 갖는다 (연관관계의 주인이 아님)
    // 연관관계의 주인을 mappedBy로 설정
    // 부모 엔티티(Order)의 영속성 상태를 자식 엔티티(OrderItem)에 전이하는 Cascade 옵션.
    @OneToMany(mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,     // 고아 객체 삭제
            fetch = FetchType.LAZY
    )
    private List<OrderItem> orderItems = new ArrayList<>();
    // 일대다 : 하나의 주문이 여러개의 주문 상품을 가지므로 List 자료형으로 매핑
}

