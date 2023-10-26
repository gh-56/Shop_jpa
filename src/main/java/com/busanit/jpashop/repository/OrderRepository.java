package com.busanit.jpashop.repository;

import com.busanit.jpashop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
