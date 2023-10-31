package com.erser.jpashop.repository;

import com.erser.jpashop.dto.ItemSearchDto;
import com.erser.jpashop.dto.MainItemDto;
import com.erser.jpashop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
