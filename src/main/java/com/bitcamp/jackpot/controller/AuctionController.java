package com.bitcamp.jackpot.controller;

import com.bitcamp.jackpot.dto.AuctionDTO;
import com.bitcamp.jackpot.service.AuctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/auction")
public class AuctionController {
    private final AuctionService auctionService;

    @GetMapping("/register")
    public void register(AuctionDTO auctionDTO) {
        auctionService.register(auctionDTO);
    }
}
