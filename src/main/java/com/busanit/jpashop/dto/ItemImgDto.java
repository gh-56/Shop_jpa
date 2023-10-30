package com.busanit.jpashop.dto;

import com.busanit.jpashop.entity.BaseEntity;
import com.busanit.jpashop.entity.Item;
import com.busanit.jpashop.entity.ItemImg;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ItemImgDto {
    private Long id;
    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    // ItemImg (상품이미지 엔티티) -> 상품이미지 DTO
    public static ItemImgDto of(ItemImg itemImg) {
        return modelMapper.map(itemImg, ItemImgDto.class);
    }
}
