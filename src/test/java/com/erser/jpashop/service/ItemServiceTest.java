package com.erser.jpashop.service;

import com.erser.jpashop.constant.ItemSellStatus;
import com.erser.jpashop.dto.ItemFormDto;
import com.erser.jpashop.entity.Item;
import com.erser.jpashop.repository.ItemImgRepository;
import com.erser.jpashop.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ItemServiceTest {
    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemImgRepository itemImgRepository;

    @Test
    @DisplayName("상품 등록 테스트")
    void saveItem() {
        // given
        // dto 추가
        ItemFormDto itemFormDto = new ItemFormDto();
        itemFormDto.setItemNm("테스트 상품");
        itemFormDto.setItemDetail("테스트 상품 상세");
        itemFormDto.setItemSellStatus(ItemSellStatus.SELL);
        itemFormDto.setPrice(10000);
        itemFormDto.setStockNumber(10);

        // 가짜 상품 이미지 파일 추가
        List<MultipartFile> multipartFiles = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            String path = "/home/gh/shop/item";
            String imageName = "image" + i + ".jpg";
            MockMultipartFile mockMultipartFile = new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1, 2, 3, 4,});
            multipartFiles.add(mockMultipartFile);
        }

        // when
        Long id = itemService.saveItem(itemFormDto, multipartFiles);

        // then
        Item item = itemRepository.findById(id).orElse(null);

        assertThat(item.getItemNm()).isEqualTo(itemFormDto.getItemNm());
    }
}