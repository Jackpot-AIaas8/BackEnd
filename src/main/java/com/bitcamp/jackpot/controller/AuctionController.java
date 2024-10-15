package com.bitcamp.jackpot.controller;

import com.bitcamp.jackpot.dto.AuctionDTO;
import com.bitcamp.jackpot.dto.ShopDTO;
import com.bitcamp.jackpot.service.AuctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/auction")
public class AuctionController {
    private final AuctionService auctionService;

    @PostMapping("/register")
    public void register(@RequestBody AuctionDTO auctionDTO, @RequestBody int shopId) {
        auctionService.register(auctionDTO, shopId);
    }
}
