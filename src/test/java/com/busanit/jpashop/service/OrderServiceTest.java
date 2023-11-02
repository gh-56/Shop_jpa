package com.busanit.jpashop.service;

import com.busanit.jpashop.constant.ItemSellStatus;
import com.busanit.jpashop.dto.OrderDto;
import com.busanit.jpashop.entity.Item;
import com.busanit.jpashop.entity.Member;
import com.busanit.jpashop.entity.Order;
import com.busanit.jpashop.entity.OrderItem;
import com.busanit.jpashop.repository.ItemRepository;
import com.busanit.jpashop.repository.MemberRepository;
import com.busanit.jpashop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    OrderRepository orderRepository;

    @Test
    void order() {
        // given
        Item item = new Item();
        item.setItemNm("상품명");
        item.setPrice(10000);
        item.setItemDetail("상품상세");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        Item savedItem = itemRepository.save(item);

        // for OrderDto
        OrderDto orderDto = new OrderDto();
        orderDto.setItemId(savedItem.getId());
        orderDto.setCount(10);

        // for email
        Member member = new Member();
        member.setEmail("test@test.com");
        Member savedMember = memberRepository.save(member);

        String email = savedMember.getEmail();

        // when
        Long orderId = orderService.order(orderDto, email);


        // then
        Order order = orderRepository.findById(orderId).orElse(null);

        List<OrderItem> orderItems = order.getOrderItems();

        int totalPrice = orderDto.getCount() * item.getPrice(); // 10 * 10000

        // 영속성 저장된 주문도 같은 가격인지 검증
        Assertions.assertThat(totalPrice).isEqualTo(order.getTotalPrice());

    }
}