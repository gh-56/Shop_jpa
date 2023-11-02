package com.erser.jpashop.service;

import com.erser.jpashop.dto.OrderDto;
import com.erser.jpashop.entity.Item;
import com.erser.jpashop.entity.Member;
import com.erser.jpashop.entity.Order;
import com.erser.jpashop.entity.OrderItem;
import com.erser.jpashop.repository.ItemRepository;
import com.erser.jpashop.repository.MemberRepository;
import com.erser.jpashop.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    public Long order(OrderDto orderDto, String email) {
        // 상품 정보 가져오기
        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        // 회원 정보 가져오기
        Member member = memberRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();
        // 상품과 개수로 OrderItem 생성 => 재고 줄이기
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount() );
        orderItemList.add(orderItem);

        // 주문 생성
        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);
        return order.getId();
    }
}
