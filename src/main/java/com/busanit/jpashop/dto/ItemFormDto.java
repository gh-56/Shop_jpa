package com.busanit.jpashop.dto;

import com.busanit.jpashop.constant.ItemSellStatus;
import com.busanit.jpashop.entity.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

// 상품을 등록할 때 사용자로부터 입력받아 전달하는 데이터
@Getter @Setter
public class ItemFormDto {
    private Long id;

    @NotBlank(message = "필수입력 값입니다.")
    private String itemNm;
    @NotNull(message = "필수입력 값입니다.")
    private Integer price;
    @NotNull(message = "필수입력 값입니다.")
    private Integer stockNumber;
    @NotBlank(message = "필수입력 값입니다.")
    private String itemDetail;

    private ItemSellStatus itemSellStatus;

    // 상품 저장 후 이미지 정보를 저장하는 리스트
    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    // 상품의 이미지 아이디를 저장하는 리스트
    private List<Long> itemImgIds = new ArrayList<>();


    private static ModelMapper modelMapper = new ModelMapper();

    // Item(엔티티) => ItemFormDto
    public static ItemFormDto of(Item item) {
        return modelMapper.map(item, ItemFormDto.class);
    }

    // 생성 메서드
    // 모델 매퍼 사용하여 Data를 두 객체 (원본 -> 목적)간에 매핑하여 반환
    public Item createItem() {
        return modelMapper.map(this, Item.class);
    }
}
