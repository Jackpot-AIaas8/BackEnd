package com.bitcamp.jackpot.service;

import com.bitcamp.jackpot.domain.Auction;
import com.bitcamp.jackpot.domain.Member;
import com.bitcamp.jackpot.domain.Orders;
import com.bitcamp.jackpot.domain.Shop;
import com.bitcamp.jackpot.dto.AuctionDTO;
import com.bitcamp.jackpot.dto.OrdersDTO;
import com.bitcamp.jackpot.dto.ShopDTO;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public interface AuctionService {
    @Transactional
    void register(AuctionDTO auctionDTO, int shopId);

    void edit(AuctionDTO auctionDTO);

    void remove(Integer auctionId);

    AuctionDTO findOne(Integer auctionId);

    List<AuctionDTO> findAll();

    default Auction dtoToEntity(AuctionDTO dto, Shop shop) {
        return Auction.builder()
                .start_time(dto.getStartTime())
                .end_time(dto.getEndTime())
                .start_price(dto.getStartPrice())
                .end_price(dto.getEndPrice())
                .shop(shop)
                .build();
    }
}
