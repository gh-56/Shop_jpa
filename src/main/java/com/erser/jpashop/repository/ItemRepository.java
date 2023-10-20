package com.erser.jpashop.repository;

import com.erser.jpashop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    // 쿼리 메소드
    // find + (엔티티 이름:생략 가능) + By + (변수 이름)

    // SELECT * FROM item WHERE itemNm = ?
    List<Item> findByItemNm(String itemNm);

    List<Item> findByPrice(Integer price);

    List<Item> findByItemDetail(String itemDetail);

    // SELECT * FROM item WHERE price < ?
    List<Item> findByPriceLessThan(Integer price);

    // SELECT * FROM item WHERE ItemNm = ? or ItemDetail = ?
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    // 가격 순으로 조회. 내림차순 조회, 특정 가격보다 적은 경우 => 쿼리 메소드
    // SELECT * FROM item WHERE price < ? DESC
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    // @Query Method 사용 : JPQL, native Query
    // 상품 상세명에 포함된 글자를 기준으로 가격 기준 내림차순
    @Query(value = "SELECT * FROM item WHERE item_detail LIKE %:itemDetail% ORDER BY price DESC", nativeQuery = true)
    List<Item> findByItemDetailNative(@Param("itemDetail") String itemDetail);

    // JPQL
    @Query(value = "SELECT i FROM Item i WHERE i.itemDetail LIKE %:itemDetail% ORDER BY price DESC")
    List<Item> findByItemDetailJPQL(@Param("itemDetail") String itemDetail);
}
