package com.erser.jpashop.entity;

import com.erser.jpashop.constant.ItemSellStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Table(name="item") // 테이블을 다른 이름 설정 가능
@Entity
@Getter @Setter @ToString
public class Item {
    @Id
    @Column(name="item_id")     // 컬럼명 다른 이름 설정 가능
    @GeneratedValue(strategy = GenerationType.AUTO) // 자동 생성
    private Long id;
    @Column(nullable = false, length = 50)  // 필수(Not Null)
    private String itemNm;  // 상품명
    @Column(nullable = false)
    private Integer price;  // 상품 가격
    @Column(nullable = false)
    private Integer stockNumber;    // 재고 수량
    @Lob        // LargeObject BLOB(바이너리), CLOB(텍스트) 타입 매핑
    @Column(nullable = false)
    private String itemDetail;  // 상품 상세
    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; // 판매 상태
    private LocalDateTime regTime;  // 등록 시간
    private LocalDateTime updateTime; // 수정 시간
}