package com.busanit.jpashop.repository;

import com.busanit.jpashop.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 로그인한 사용자 주문 데이터 가져오기
    // JPQL 로 email 기준 주문 조회
    @Query("select o from Order o " +
            "where o.member.email = :email " +
            "order by o.orderDate desc")
    List<Order> findOrder(@Param("email") String email, Pageable pageable);

    // 페이징을 위한 전체 주문 갯수 조회
    @Query("select count(o) from Order o " +
            "where o.member.email = :email")
    Long countOrder(@Param("email")  String email);
}
