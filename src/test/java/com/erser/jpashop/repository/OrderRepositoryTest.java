package com.erser.jpashop.repository;

import com.erser.jpashop.constant.ItemSellStatus;
import com.erser.jpashop.entity.Item;
import com.erser.jpashop.entity.Order;
import com.erser.jpashop.entity.OrderItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderRepositoryTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    @PersistenceContext
    EntityManager em;

    // 상품 생성
    public Item createItem(){
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        return item;
    }
    @Test
    void 영속성_전이_테스트() {
        Order order = new Order();
        for (int i = 0; i < 3; i++) {
            Item item = createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setOrders(order);
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            order.getOrderItems().add(orderItem);
        }
        orderRepository.saveAndFlush(order); // 저장하면서 영속성 컨텍스트 -> 반영
        em.clear(); // 영속성 컨텍스트 초기화

        // when : 저장된 주문 엔티티를 DB에서 조회
        Order savedOrder = orderRepository.findById(order.getId()).orElse(null);

        // then : 저장한 주문상품 엔티티 갯수로 테스트 검증
        Assertions.assertThat(savedOrder.getOrderItems().size()).isEqualTo(3);
    }
}