package com.erser.jpashop.repository;

import com.erser.jpashop.entity.Item;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ItemRepositoryEMTest {
    @Autowired
    ItemRepositoryEM itemRepositoryEM;

    @Test
    void testItem(){
        Item item = new Item();
        item.setItemNm("상품명");
        item.setPrice(10000);
        item.setItemDetail("상세설명");
        item.setStockNumber(100);
        Item savedItem = itemRepositoryEM.save(item);

        Item findItem = itemRepositoryEM.find(savedItem.getId());

        // print
        System.out.println(savedItem);
        System.out.println(findItem);
        // assert
        // 동일성 보장
        assertEquals(savedItem.toString(), findItem.toString());
    }
}