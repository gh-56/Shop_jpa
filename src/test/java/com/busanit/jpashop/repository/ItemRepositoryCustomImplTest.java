package com.busanit.jpashop.repository;

import com.busanit.jpashop.constant.ItemSellStatus;
import com.busanit.jpashop.dto.ItemSearchDto;
import com.busanit.jpashop.dto.MainItemDto;
import com.busanit.jpashop.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemRepositoryCustomImplTest {

    @Autowired
    ItemService itemService;
    @Test
    void getMainItemPage() {
        // given DTO, pageable
        ItemSearchDto itemSearchDto = new ItemSearchDto();
        itemSearchDto.setSearchDateType(null);
        itemSearchDto.setSearchSellStatus(ItemSellStatus.SELL);
        itemSearchDto.setSearchBy("itemNm");
        itemSearchDto.setSearchQuery(null);

        Pageable pageable = PageRequest.of(0, 6);

        // when
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);

        // then
        System.out.println("전체페이지 : " + items.getTotalElements());
    }
}