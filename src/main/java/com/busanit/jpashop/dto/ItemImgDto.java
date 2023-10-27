package com.busanit.jpashop.dto;

import com.busanit.jpashop.entity.BaseEntity;
import com.busanit.jpashop.entity.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemImgDto {
    private Long id;
    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private String repImgYn;
}
