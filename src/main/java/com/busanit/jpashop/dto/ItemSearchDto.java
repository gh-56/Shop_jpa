package com.busanit.jpashop.dto;

import com.busanit.jpashop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDto {
    private String searchDateType;
    private ItemSellStatus searchSellStatus;
    private String searchBy;
    private String searchQuery =""; // = ""; null 문제 발생시 초기화

}
