package com.busanit.jpashop.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** 상품_이미지파일 | item_img */

@Entity
@Table(name = "item_img")
@Getter @Setter
public class ItemImg extends BaseEntity {

    // 상품이미지id
    @Id @GeneratedValue
    @Column(name = "item_img_id")
        private Long id;

        // 서버 이미지 파일 이름
        @Column
        private String imgName;

        // 원본 파일 이름
        @Column
        private String oriImgName;

        // 이미지 조회 URL
        @Column
        private String imgUrl;

        // 대표이미지여부
        @Column
        private String repImgYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

}


