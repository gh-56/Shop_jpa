package com.erser.jpashop.controller;

import com.erser.jpashop.dto.ItemFormDto;
import com.erser.jpashop.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    @GetMapping("admin/item/new")
    public String itemForm(Model model){
        model.addAttribute("itemFormDto", new ItemFormDto());
//        model.addAttribute("itemImgDto", new ItemImgDto());
        return "/item/itemForm";
    }

    @PostMapping("admin/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, @RequestParam("itemImgFile") List<MultipartFile> itemImgFiles){
        // 유효성 검증 : 통과하지 못한 경우 폼으로
        if(bindingResult.hasErrors()){
            return "/item/itemForm";
        }
        // 아이템 DB 저장
        itemService.saveItem(itemFormDto, itemImgFiles);
        // 리다이렉트
        return "redirect:/";
    }
}
