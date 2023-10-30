package com.busanit.jpashop.service;

import com.busanit.jpashop.entity.ItemImg;
import com.busanit.jpashop.repository.ItemImgRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemImgService {

    private final ItemImgRepository itemImgRepository;
    private final FileService fileService;

    // application.properties에서 저장한 값을 불러와서 해당 필드 변수에 넣어준다.
    @Value("${itemImgLocation}")
    private String itemImgLocation;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) {

        // 원본 파일 이름
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";        // 저장할 파일 이름
        String imgUrl = "";         // 이미지 불러올 경로

        // 파일 업로드
        if (StringUtils.hasText(oriImgName)) {
            imgName = fileService.uploadFile(itemImgLocation,oriImgName, itemImgFile);  // 저장된 파일의 이름 (고유한 파일을 만들어서 로컬
            imgUrl = "/images/item/"+imgName;
        }

        // 상품 이미지 정보 저장
        itemImg.updateItemImg(oriImgName, imgName, imgUrl);
        itemImgRepository.save(itemImg);
    }

    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) {
        // 예외처리 : 파일 입력 태그가 수정이 된 경우만 로직을 수행
        if(!itemImgFile.isEmpty()) {
            ItemImg itemImg = itemImgRepository.findById(itemImgId).orElseThrow(EntityNotFoundException::new);
            String oriImgName = itemImgFile.getOriginalFilename();
            // 파일 업로드 - 리턴 UUID 파일이름
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile);
            // 경로
            String imgUrl = "/images/item/" + imgName;
            // 변경 감지 이미지 변경이 발생
            itemImg.updateItemImg(oriImgName, imgName, imgUrl);
            
        }
    }
}
