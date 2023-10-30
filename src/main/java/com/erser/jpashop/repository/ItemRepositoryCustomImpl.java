package com.erser.jpashop.repository;

import com.erser.jpashop.dto.ItemSearchDto;
import com.erser.jpashop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {
    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        // QueryDSL + Paging
        return null;
    }
}
