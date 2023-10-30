package com.erser.jpashop.service;

import com.erser.jpashop.dto.ItemFormDto;
import com.erser.jpashop.dto.ItemImgDto;
import com.erser.jpashop.entity.Item;
import com.erser.jpashop.entity.ItemImg;
import com.erser.jpashop.repository.ItemImgRepository;
import com.erser.jpashop.repository.ItemRepository;
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
    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFiles){
        // Item 상품 등록
        Item item = itemFormDto.createItem(); // 아이템 엔티티 만들기, 모델매퍼, 생성메서드 사용
        itemRepository.save(item);
        // ItemImage 이미지 등록
        for (int i = 0; i < itemImgFiles.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);  // item 연관 관계
            if(i==0){
                itemImg.setRepImgYn("Y");   // 리스트의 첫번째 이미지는 대표이미지
            } else {
                itemImg.setRepImgYn("N");
            }
            itemImgService.saveItemImg(itemImg, itemImgFiles.get(i));
        }
        return item.getId();
    }

    public ItemFormDto getItemDto(Long itemId) {
        // 아이템 불러오기
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        // 엔티티 -> dto
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        // 아이뎀 이미지 불러오기
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        // 엔티티 -> dto
        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }
        // 아이템 폼 DTO에 이미지 DTO 넣기
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    }

    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) {
        Item item = itemRepository.findById(itemFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        // 변경 감지 기능을 사용 :
        // 리포지터리에 저장하는 로직을 호출하지 않고,
        // 영속 상태인 데이터를 변경하게 될 경우,
        // 트랜잭션이 종료될 때, 변경 감지 기능이 작동
        item.updateItem(itemFormDto);

        // 상품 이미지 조회
        List<Long> itemImgIds = itemFormDto.getItemImgIds();
        for (int i = 0; i < itemImgFileList.size(); i++){
            // 상품 파일 서비스 계층에 수정 위임 : id, file
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }
        return item.getId();
    }
}
