package com.busanit.jpashop.repository;

import com.busanit.jpashop.dto.ItemSearchDto;
import com.busanit.jpashop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
