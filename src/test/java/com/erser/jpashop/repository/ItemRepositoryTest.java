package com.erser.jpashop.repository;

import com.erser.jpashop.constant.ItemSellStatus;
import com.erser.jpashop.entity.Item;
import com.erser.jpashop.entity.QItem;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties") // 해당 테스트 실행시 환경 설정 소스 우선순위를 변경한다.
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;
    // 영속성 컨텍스트 사용, 스프링 컨테이너 의존성 주
    @PersistenceContext
    EntityManager em;

    // 가상의 아이템 10개를 만드는 셋업 메소드
    public void createItemList(){
        for (int i = 0; i < 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setStockNumber(100 + i);
            item.setItemDetail("상세정보" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }
    }

    @Test
    void saveTest(){
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setStockNumber(100);
        item.setItemDetail("상세정보");
        item.setItemSellStatus(ItemSellStatus.SELL);
        // when
        Item savedItem = itemRepository.save(item);
        // then
        System.out.println(savedItem);
        assertEquals(item,savedItem);
    }
    @Test
    void findByItemTest() {
        // given
        createItemList();
        String findItemNm = "테스트 상품3";
        // when
        List<Item> itemList = itemRepository.findByItemNm(findItemNm);

        // then삼
        // 눈으로 체크
        itemList.forEach(System.out::println);
        // AssertJ : assertThat(actual).isEqualTo(expected)
        assertThat(itemList.get(0).getItemNm()).isEqualTo(findItemNm);
    }

    @Test
    void findByPriceTest() {
        // given
        createItemList();
        int price = 10002;

        // when
        List<Item> findPrice = itemRepository.findByPrice(price);

        // then
        assertThat(findPrice.get(0).getPrice()).isEqualTo(price);
    }

    @Test
    void findByItemDetailTest() {
        // given
        createItemList();
        String findItemDetail = "상세정보3";

        // when
        List<Item> byItemDetail = itemRepository.findByItemDetail(findItemDetail);

        // then
        assertThat(byItemDetail.get(0).getItemDetail()).isEqualTo(findItemDetail);
    }

    @Test
    void findByPriceLessThanTest() {
        createItemList();
        List<Item> byPriceLessThan = itemRepository.findByPriceLessThan(10005);
        byPriceLessThan.forEach(System.out::println);
        assertThat(byPriceLessThan.size()).isEqualTo(5);
    }

    @Test
    void findByPriceLessThanOrderByPriceDescTest() {
        createItemList();
        List<Item> priceLessThanDesc = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        priceLessThanDesc.forEach(System.out::println);
        assertThat(priceLessThanDesc.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("native Query 테스트")
    void findByItemDetailNativeTest() {
        // given
        createItemList();
//        String keyword = "상";
        // when
        List<Item> itemList = itemRepository.findByItemDetailNative("3");
        // then
        itemList.forEach(System.out::println);
    }

    @Test
    @DisplayName("JQPL 테스트")
    void findByItemDetailJPQLTest() {
        createItemList();
        List<Item> itemList = itemRepository.findByItemDetailJPQL("2");
        itemList.forEach(System.out::println);
    }

    @Test
    @DisplayName("querydsl 테스트")
    void queryDslTest() {
        // given
        createItemList();

        // when
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        // Q타입 객체를 사용하여 Querydsl 쿼리 생성 (자동으로 생성)
        QItem qItem = QItem.item;
        JPAQuery<Item> query = jpaQueryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%상세%"))
                .orderBy(qItem.price.desc());
        List<Item> itemList = query.fetch();

        // then
        itemList.forEach(System.out::println);
    }
}