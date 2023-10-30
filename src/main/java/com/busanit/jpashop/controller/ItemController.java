package com.busanit.jpashop.controller;

import com.busanit.jpashop.dto.ItemDto;
import com.busanit.jpashop.dto.ItemFormDto;
import com.busanit.jpashop.service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/admin/item/new")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "/item/itemForm";
    }

    @PostMapping("/admin/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto,
                          BindingResult bindingResult,                          @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList ) {
        // 유효성 검증 : 통과하지 못한 경우 폼으로
        if (bindingResult.hasErrors()) {
            return "/item/itemForm";
        }
        // 아이템 DB에 저장
        itemService.saveItem(itemFormDto, itemImgFileList);
        // 리다이렉트
        return "redirect:/";
    }

    // 아이템 수정 Get 요청
    @GetMapping("/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model) {
        
        try {
            // 아이템 서비스에 위임하여 dto 리턴
            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
            // 엔티티가 존재할 경우, dto를 모델에 담아 뷰로 전달
            model.addAttribute("itemFormDto", itemFormDto);
            // 경로변수(itemId)에 해당하는 엔티티가 없을 경우
        } catch(EntityNotFoundException e) {
            // 상품등록 페이지로 리다이렉트
            return "redirect:/admin/item/new";
        }

        return "item/itemForm";
    }
}
