package com.erser.jpashop.controller;

import com.erser.jpashop.dto.ItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
public class ThymeleafController {
    @GetMapping("/ex01")
    public String ex01(Model model) {
        model.addAttribute("data", "hello thymeleaf!");
        return "thymeleaf/ex01";
    }

    @GetMapping("/ex02")
    public String ex02(Model model) {
        ItemDto itemDto = ItemDto.builder()
                .itemNm("상품명1")
                .itemDetail("상품상세")
                .price(10000)
                .regTime(LocalDateTime.now())
                .build();
        model.addAttribute("itemDto", itemDto);
        return "thymeleaf/ex02";
    }

    @GetMapping("/ex03")
    public String ex03(Model model){
        ArrayList<ItemDto> itemDtoList = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            ItemDto itemDto = ItemDto.builder()
                    .itemNm("상품명"+i)
                    .itemDetail("상품상세"+i)
                    .price(10000+i)
                    .regTime(LocalDateTime.now())
                    .build();
            itemDtoList.add(itemDto);
        }
        model.addAttribute("itemDtoList", itemDtoList);
        return "thymeleaf/ex03";
    }

    @GetMapping("/ex04")
    public String ex04(Model model){
        ArrayList<ItemDto> itemDtoList = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            ItemDto itemDto = ItemDto.builder()
                    .itemNm("상품명"+i)
                    .itemDetail("상품상세"+i)
                    .price(10000+i)
                    .regTime(LocalDateTime.now())
                    .build();
            itemDtoList.add(itemDto);
        }
        model.addAttribute("itemDtoList", itemDtoList);
        return "thymeleaf/ex04";
    }

    @GetMapping("/ex05")
    public String ex05(Model model){
        return "thymeleaf/ex05";
    }

    @GetMapping("/ex06")
    public String ex06(Model model, String param1, String param2){
        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);
        return "thymeleaf/ex06";
    }

    @GetMapping("/ex07")
    public String ex07(Model model){
        return "thymeleaf/ex07";
    }
}
