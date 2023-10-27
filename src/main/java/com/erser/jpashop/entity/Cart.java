package com.erser.jpashop.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** 장바구니 | cart */

@Entity
@Table(name = "cart")
@Getter @Setter @ToString
@NoArgsConstructor
public class Cart extends BaseEntity{

    // 장바구니id
    @Id
    @Column(name = "cart_id")
    @GeneratedValue
    private Long id;

    // 1대1 단방향 매핑
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
