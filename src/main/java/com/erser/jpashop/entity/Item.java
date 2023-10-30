package com.erser.jpashop.entity;

import com.erser.jpashop.constant.ItemSellStatus;
import com.erser.jpashop.dto.ItemFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name="item") // 테이블을 다른 이름 설정 가능
@Entity
@Getter @Setter @ToString
public class Item extends BaseEntity{
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

    // 영속성 컨택스트 변경감지기능 활용 : 트랜잭션 종료시 업데이트 쿼리 수행
    public void updateItem(ItemFormDto itemFormDto) {
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    // Auditing 추가 했기 때문에 주석 처리함
//    private LocalDateTime regTime;  // 등록 시간
//    private LocalDateTime updateTime; // 수정 시간
}