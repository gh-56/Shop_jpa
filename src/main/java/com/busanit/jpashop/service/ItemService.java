package com.busanit.jpashop.service;

import com.busanit.jpashop.dto.ItemFormDto;
import com.busanit.jpashop.dto.ItemImgDto;
import com.busanit.jpashop.entity.Item;
import com.busanit.jpashop.entity.ItemImg;
import com.busanit.jpashop.repository.ItemImgRepository;
import com.busanit.jpashop.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    // 의존성 주입
    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final ItemImgService itemImgService;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) {
        // Item 상품 등록
        
        Item item = itemFormDto.createItem(); // 아이템 엔티티 만들기, 모델매퍼, 생성메서드 사용
        itemRepository.save(item);
        
        // ItemImage 이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);          // item 연관관계
            if (i==0) {
                itemImg.setRepImgYn("Y");   // 리스트의 첫번째 이미지는 대표이미지
            } else {
                itemImg.setRepImgYn("N");
            }
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
        return item.getId();
    }

    public ItemFormDto getItemDtl(Long itemId) {
        // 아이템 불러오기
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        // 엔티티 -> dto
        ItemFormDto itemFormDto = ItemFormDto.of(item);

        // 아이템 이미지 불러오기
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);

        // 엔티티 -> dto (5개 목록)
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        // 아이템 폼 DTO에 이미지 DTO 넣기
        itemFormDto.setItemImgDtoList(itemImgDtoList);

        return itemFormDto;
    }
}
