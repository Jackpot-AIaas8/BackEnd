package com.bitcamp.jackpot.controller;

import com.bitcamp.jackpot.dto.OrdersDTO;
import com.bitcamp.jackpot.dto.PageRequestDTO;
import com.bitcamp.jackpot.dto.PageResponseDTO;
import com.bitcamp.jackpot.dto.ShopDTO;
import com.bitcamp.jackpot.repository.MemberRepository;
import com.bitcamp.jackpot.repository.ShopRepository;
import com.bitcamp.jackpot.service.MemberService;
import com.bitcamp.jackpot.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop")
@Log4j2
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/findOne/{shopId}")
    public ShopDTO findOne(@PathVariable("shopId") int shopId) {
//        log.info(shopId);
        return shopService.findOne(shopId);
    }

    @GetMapping("/findList")
    public PageResponseDTO<ShopDTO> findList(PageRequestDTO pageRequestDTO) {
//        log.info(pageRequestDTO);
        return shopService.findList(pageRequestDTO);
    }

    @GetMapping("/findOrderList")
    public List<OrdersDTO> findOrderList(PageRequestDTO pageRequestDTO) {
        List<OrdersDTO> result = shopService.findOrderList(pageRequestDTO);
        log.info(result);
        return (List<OrdersDTO>) result;
    }



    @GetMapping("/search")
    public PageResponseDTO<ShopDTO> search(@RequestParam("name") String name, PageRequestDTO pageRequestDTO) {
//        log.info(name);
        return shopService.search(name, pageRequestDTO);
    }

    @GetMapping("/category/{category}")
    public PageResponseDTO<ShopDTO> getProductsByCategory(@PathVariable("category") int category, PageRequestDTO pageRequestDTO) {
//        log.info("Category: {}", category);
        return shopService.findByCategory(category, pageRequestDTO);
    }

}
