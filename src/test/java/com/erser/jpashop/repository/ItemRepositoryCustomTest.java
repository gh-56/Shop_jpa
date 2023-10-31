package com.erser.jpashop.repository;

import com.erser.jpashop.constant.ItemSellStatus;
import com.erser.jpashop.dto.ItemSearchDto;
import com.erser.jpashop.dto.MainItemDto;
import com.erser.jpashop.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
class ItemRepositoryCustomTest {
    @Autowired
    ItemService itemService;

    @Test
    void getMainItemPage(){
        // given
        ItemSearchDto itemSearchDto = new ItemSearchDto();
        itemSearchDto.setSearchDateType(null);
        itemSearchDto.setSearchSellStatus(ItemSellStatus.SELL);
        itemSearchDto.setSearchBy("itemNm");
        itemSearchDto.setSearchQuery(null)
        ;
        Pageable pageable = PageRequest.of(0, 6);
        // when
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);
        // then
        System.out.println("전체페이지 : " + items.getTotalElements());
    }

}