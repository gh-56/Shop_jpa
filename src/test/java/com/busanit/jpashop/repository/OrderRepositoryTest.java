package com.busanit.jpashop.repository;

import com.busanit.jpashop.constant.ItemSellStatus;
import com.busanit.jpashop.entity.Item;
import com.busanit.jpashop.entity.Member;
import com.busanit.jpashop.entity.Order;
import com.busanit.jpashop.entity.OrderItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderRepositoryTest {

    // 의존성 주입 - 주문, 상품, 회원, 주문 상품 영속성
    @Autowired OrderRepository orderRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired OrderItemRepository orderItemRepository;

    @PersistenceContext
    EntityManager em;

    // 상품 생성
    public Item createItem() {
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
        // given 주문 데이터 준비
        Order order = new Order();
        for (int i = 0; i < 3; i++) {
            Item item = createItem();
            itemRepository.save(item);
            // 주문 상품 준비
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            //
            order.getOrderItems().add(orderItem);   // order 엔티티에 orderItem 엔티티 추가
        }
        orderRepository.saveAndFlush(order);  // 저장하면서 영속성 컨텍스트 -> 반영
        em.clear();     // 영속성 컨텍스트 초기화

        // when : 저장된 주문 엔티티를 DB에서 조회
        Order savedOrder = orderRepository.findById(order.getId()).orElse(null);
        // then : 저장한 주문상품 엔티티 갯수로 테스트 검증
        Assertions.assertThat(savedOrder.getOrderItems().size()).isEqualTo(3);
    }
    // given : 주문 데이터 준비 (Order 객체에 (주문상품 3개), 멤버) 추가)
    public Order createOrder() {
        Order order = new Order();
        for (int i = 0; i < 3; i++) {
            Item item = createItem();
            itemRepository.save(item);
            // 주문 상품 준비
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            //
            order.getOrderItems().add(orderItem);   // order 엔티티에 orderItem 엔티티 추가
        }

        Member member = new Member();
        memberRepository.save(member);

        order.setMember(member);
        orderRepository.save(order);
        return order;
    }

    @Test
    void 고아객체_테스트() {
        Order order = createOrder();
        order.getOrderItems().remove(0);
        // Order 엔티티에서 관리하는 orderItem 첫번째 요소 제거
        // Order - orderItem 이 제거가 되면서
        // insert문으로 삽입된 orderItem (첫번째) 객체가 고아(orphan)가 됨
        // 고아 객체 제거 옵션을 통해 삭제 (orphanRemoval = true)
        em.flush();
    }

    @Test
    void 지연_로딩_테스트() {
        // 주문 데이터를 생성하고 저장
        Order order = createOrder();
        // 주문 정보 내 첫번째 주문 상품 아이디를 가져옴.
        Long id = order.getOrderItems().get(0).getId();

        // 영속성 컨텍스트 비우기
        em.flush();
        em.clear();

        // 주문상품을 데이터베이스에서 조회
        OrderItem orderItem = orderItemRepository.findById(id).orElse(null);

        // EAGER : 주문 상품 id를 조회하는 것임에도, 관련 연관관계가 있는 모든 테이블을 join 하여 조회
        // LAZY : 사용하지 않는 필드는 가져오지 않음.

        // 주문 상품이 가지고 있는 연관관계 테이블의 클래스 확인
        System.out.println("주문 클래스 : " + orderItem.getOrder().getClass());
        System.out.println("======================");
        orderItem.getOrder().getOrderDate();
        System.out.println("======================");
    }
}