package com.busanit.jpashop.repository;

import com.busanit.jpashop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
