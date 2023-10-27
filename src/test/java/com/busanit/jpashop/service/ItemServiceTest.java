package com.busanit.jpashop.service;

import com.busanit.jpashop.constant.ItemSellStatus;
import com.busanit.jpashop.dto.ItemFormDto;
import com.busanit.jpashop.entity.Item;
import com.busanit.jpashop.repository.ItemImgRepository;
import com.busanit.jpashop.repository.ItemRepository;
import org.assertj.core.api.Assertions;
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
import static org.junit.jupiter.api.Assertions.*;

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
        itemFormDto.setItemNm("테스트상품");
        itemFormDto.setItemDetail("테스트상품 상세");
        itemFormDto.setItemSellStatus(ItemSellStatus.SELL);
        itemFormDto.setPrice(10000);
        itemFormDto.setStockNumber(10);

        // 가짜 상품 이미지 파일 추가
        List<MultipartFile> multipartFileList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            String path = "C:/shop/item/";
            String imageName = "image" + i + ".jpg";
            MockMultipartFile mockMultipartFile = new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1, 2, 3, 4});
            multipartFileList.add(mockMultipartFile);
        }

        // when
        Long id = itemService.saveItem(itemFormDto, multipartFileList);

        // then
        Item item = itemRepository.findById(id).orElse(null);

        assertThat(item.getItemNm()).isEqualTo(itemFormDto.getItemNm());
    }
}