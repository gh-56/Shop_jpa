package com.busanit.jpashop.repository;

import com.busanit.jpashop.entity.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional      // 엔티티 매니저 사용
@SpringBootTest
class ItemRepositoryEMTest {

    @Autowired
    ItemRepositoryEM itemRepositoryEM;

    @Test
    void testItem() {
        // given
        Item item = new Item();
        item.setItemNm("상품명");
        item.setPrice(10000);
        item.setItemDetail("상세설명");
        item.setStockNumber(100);
        Item savedItem = itemRepositoryEM.save(item);

        // when
        Item findItem = itemRepositoryEM.find(savedItem.getId());

        // print
        System.out.println(savedItem);
        System.out.println(findItem);

        // assert
        // 동일성 보장
        assertEquals(savedItem, findItem);
        assertEquals(savedItem.toString(), findItem.toString());
    }
}