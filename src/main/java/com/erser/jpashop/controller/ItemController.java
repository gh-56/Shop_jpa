package com.erser.jpashop.controller;

import com.erser.jpashop.dto.ItemFormDto;
import com.erser.jpashop.dto.ItemSearchDto;
import com.erser.jpashop.entity.Item;
import com.erser.jpashop.service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("admin/item/new")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
//        model.addAttribute("itemImgDto", new ItemImgDto());
        return "/item/itemForm";
    }

    @PostMapping("admin/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                          @RequestParam("itemImgFile") List<MultipartFile> itemImgFiles,
                          Model model) {
        // 유효성 검증 : 통과하지 못한 경우 폼으로
        if (bindingResult.hasErrors()) {
            return "/item/itemForm";
        }

        // 상품 이미지 파일 1개를 필수적으로 넣는 유효성 검증 추가
        if(itemImgFiles.get(0).isEmpty()){
            model.addAttribute("errorMessage", "첫 번째 상품 이미지는 필수 입력값입니다.");
            return "/item/itemForm";
        }

        // 아이템 DB 저장
        itemService.saveItem(itemFormDto, itemImgFiles);
        // 리다이렉트
        return "redirect:/";
    }

    @GetMapping("/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model) {
        try {
            // 아이템 서비스에 위임하여 dto 리턴
            ItemFormDto itemFormDto = itemService.getItemDto(itemId);
            // 엔티티가 존재할 경우 dto를 모델에 담아 뷰로 전달
            model.addAttribute("itemFormDto", itemFormDto);
            // 경로변수(itemId)에 해당하는 엔티티가 없을 경우
        } catch (EntityNotFoundException e) {
            // 상품등록 페이지로 리다이렉트
            return "redirect:/admin/item/new";
        }
        return "item/itemForm";
    }

    @PostMapping("/admin/item/{itemId}")
    public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList,
                             Model model) {
        // 유효성 검증 : 통과하지 못한 경우 폼으로
        if (bindingResult.hasErrors()) {
            return "/item/itemForm";
        }

        // 상품 이미지 파일 1개를 필수적으로 넣는 유효성 검증 추가
        if(itemImgFileList.get(0).isEmpty()){
            model.addAttribute("errorMessage", "첫 번째 상품 이미지는 필수 입력값입니다.");
            return "/item/itemForm";
        }

        // 서비스 계층에 수정 비즈니스 로직 위임
        itemService.updateItem(itemFormDto, itemImgFileList);
        return "redirect:/";
    }

    @GetMapping("/admin/items")
    public String itemManage(Model model, ItemSearchDto itemSearchDto){
        Pageable pageable = PageRequest.of(0, 3);
        itemService.getAdminItemPage(itemSearchDto , pageable);

        // 서비스 계층에서 item 가져오기
        List<Item> items = itemService.getItemList();
        model.addAttribute("items", items);
        return "item/itemMng";
    }

}
