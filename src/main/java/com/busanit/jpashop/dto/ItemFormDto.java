package com.busanit.jpashop.dto;

import com.busanit.jpashop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

// 상품을 등록할 때 사용자로부터 입력받아 전달하는 데이터
@Getter @Setter
public class ItemFormDto {
    private Long id;
    private String itemNm;
    private Integer price;
    private Integer stockNumber;
    private String itemDetail;
    private ItemSellStatus itemSellStatus;

}
