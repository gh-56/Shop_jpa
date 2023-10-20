package com.erser.jpashop.entity;

import com.erser.jpashop.constant.ItemSellStatus;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class Item {
    private Long id;
    private String itemNm;  // 상품명
    private Integer price;  // 상품 가격
    private Integer stockNumber;    // 재고 수량
    private String itemDetail;  // 상품 상세
    private ItemSellStatus itemSellStatus; // 판매 상태
    private LocalDateTime regTime;  // 등록 시간
    private LocalDateTime updateTime; // 수정 시간
}