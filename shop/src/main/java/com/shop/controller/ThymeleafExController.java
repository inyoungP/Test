package com.shop.controller;

import com.shop.dto.ItemDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafExController {

    @GetMapping("/ex01")
    public String thymeleafExample01(Model model) {
        model.addAttribute("data" , "타임리프 예제입니다.");
        return "thymeleafEx/thymeleafEx01";
    }

    @GetMapping(value = ("/ex02"))
    public String thymeleafExample02(Model model) {

        ItemDTO itemDTO = new ItemDTO();

        itemDTO.setItemDetail("상품 상세 설명");
        itemDTO.setItemName("테스트 상품1");
        itemDTO.setPrice(10000);
        itemDTO.setRegTime(LocalDateTime.now());

        model.addAttribute("itemDTO", itemDTO);
        return "thymeleafEx/thymeleafEx02";
    }

    @GetMapping(value = ("/ex03"))
    public String thymeleafExample03(Model model) {
        List<ItemDTO> itemDTOList = new ArrayList<>();

        for(int i=1; i<=10; i++) {
            ItemDTO itemDTO = new ItemDTO();

            itemDTO.setItemName("테스트 상품" + i);
            itemDTO.setItemDetail("상품 상세 설명" + i);
            itemDTO.setPrice(10000*i);
            itemDTO.setRegTime(LocalDateTime.now());

            itemDTOList.add(itemDTO);
        }

        model.addAttribute("itemDTOList", itemDTOList);
        return "thymeleafEx/thymeleafEx03";
    }

    @GetMapping(value = ("/ex04"))
    public String thymeleafExample04(Model model) {
        List<ItemDTO> itemDTOList = new ArrayList<>();

        for(int i=1; i<=10; i++) {
            ItemDTO itemDTO = new ItemDTO();

            itemDTO.setItemName("테스트 상품" + i);
            itemDTO.setItemDetail("상품 상세 설명" + i);
            itemDTO.setPrice(10000*i);
            itemDTO.setRegTime(LocalDateTime.now());

            itemDTOList.add(itemDTO);
        }

        model.addAttribute("itemDTOList", itemDTOList);
        return "thymeleafEx/thymeleafEx04";
    }

    @GetMapping(value = ("/ex05"))
    public String thymeleafExample05(Model model) {
        return "thymeleafEx/thymeleafEx05";
    }

    @GetMapping(value = ("/ex06"))
    public String thymeleafExample06(Model model, String param1, String param2) {
        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);
        return "thymeleafEx/thymeleafEx06";
    }

    @GetMapping(value = ("/ex07"))
    public String thymeleafExample07() {
        return "thymeleafEx/thymeleafEx07";
    }
}