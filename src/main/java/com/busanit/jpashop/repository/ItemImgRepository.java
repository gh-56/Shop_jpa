package com.busanit.jpashop.repository;

import com.busanit.jpashop.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    // 쿼리 메소드 : 상품 아이디(외래키) 기준으로 상품 이미지 파일 엔티티 찾기(5개 목록) + 상품이미지 파일 오름차순으로 정렬
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);
}
