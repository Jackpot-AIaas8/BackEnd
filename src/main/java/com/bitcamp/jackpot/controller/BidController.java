package com.bitcamp.jackpot.controller;

import com.bitcamp.jackpot.dto.BiddingDTO;
import com.bitcamp.jackpot.service.BiddingService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/bid")
public class BidController {
    private final BiddingService biddingService;

    @PostMapping("/register")
    public void register(BiddingDTO biddingDTO) {
        log.info(biddingDTO);
        biddingService.register(biddingDTO);
    }

    @GetMapping("/before")
    public BiddingDTO findBeforBidding(int biddingId) {
        BiddingDTO biddingDTO = biddingService.findBeforeBidding(biddingId);
        return biddingDTO;
    }

    @DeleteMapping("/remove")
    public void remove() {

    }
}
