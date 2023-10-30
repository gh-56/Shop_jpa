package com.erser.jpashop.dto;

import com.erser.jpashop.entity.ItemImg;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ItemImgDto {
    private Long id;
    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private String repImgYn;

    // model mapper를 사용하여 Data를 두 객체 (원본 -> 목적)간에 매핑하여 반환
    private static ModelMapper modelMapper = new ModelMapper();
    public static ItemImgDto of(ItemImg itemImg) {
        return modelMapper.map(itemImg, ItemImgDto.class);
    }
}
