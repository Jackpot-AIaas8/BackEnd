package com.bitcamp.jackpot.controller;

import com.bitcamp.jackpot.dto.*;
import com.bitcamp.jackpot.service.AuctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/auction")
public class AuctionController {
    private final AuctionService auctionService;

    @PostMapping("/register/{shopId}")
    public void register(@RequestBody AuctionDTO auctionDTO, @PathVariable("shopId") int shopId) {
        auctionService.register(auctionDTO, shopId);
    }

    @PostMapping("/edit")
    public void editAuctionStatus(@RequestBody AuctionStatusDTO auctionStatusDTO) {
        log.info(auctionStatusDTO);
        auctionService.edit(auctionStatusDTO.getAuctionId(), auctionStatusDTO.getAuctionStatus());
    }

    @GetMapping("/findOne/{auctionId}")
    public AuctionDTO findOne(@PathVariable("auctionId") int auctionId) {
        return auctionService.findOne(auctionId);
    }

    @GetMapping("/auctionNext")
    public AuctionDTO findNextAuction() {
        return auctionService.findNextAuction();
    }

    @GetMapping("/findList")
    public PageResponseDTO<AuctionDTO> findList(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);
        return auctionService.findAll(pageRequestDTO);
    }
}
