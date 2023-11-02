package com.busanit.jpashop.controller;

import com.busanit.jpashop.dto.ItemSearchDto;
import com.busanit.jpashop.dto.MainItemDto;
import com.busanit.jpashop.entity.Item;
import com.busanit.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemService itemService;

    @GetMapping(value = {"/"})
    public String main(ItemSearchDto itemSearchDto, @RequestParam Optional<Integer> page, Model model) {

        Pageable pageable = PageRequest.of(page.isPresent()
                        ? page.get()
                        : 0,
                6);

        // 서비스 계층에서 item 페이지 가져오기
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);


        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);

        // 시작페이지, 마지막 페이지, 최대 페이지 구하기
        int maxPage = 5;
        // thymeleaf로 페이지 로직 이전
        // start = ${ (items.getNumber() / maxPage) * maxPage + 1 },
        // end = ${ (items.getTotalPages() == 0) ?  1 : (start+(maxPage-1) < items.getTotalPages()) ? (start + maxPage - 1) : items.getTotalPages() }
//        int start = (items.getNumber() / maxPage) * maxPage + 1;
//        int end = 0;
//
//        if (items.getTotalPages() == 0) {
//            end = 1;
//        } else if (start+(maxPage-1) < items.getTotalPages()) {
//            end = start + maxPage - 1;
//        } else {
//            end = items.getTotalPages();
//        }

        model.addAttribute("maxPage", maxPage);
//        model.addAttribute("start", start);
//        model.addAttribute("end", end);


        return "main";
    }
}
