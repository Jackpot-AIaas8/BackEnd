package com.bitcamp.jackpot.service;

import com.bitcamp.jackpot.domain.*;
import com.bitcamp.jackpot.dto.*;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public interface AuctionService {
    
    void register(AuctionDTO auctionDTO, int shopId);

    void edit(int auctionId, int auctionStatus);

    void remove(Integer auctionId);

    AuctionDTO findOne(int auctionId);

    AuctionDTO findNextAuction();

    PageResponseDTO<AuctionDTO> findAll(PageRequestDTO pageRequestDTO);

    default Auction dtoToEntity(AuctionDTO dto, Shop shop) {
        return Auction.builder()
//                .auctionId(dto.getAuctionId())
                .start_time(dto.getStartTime())
                .end_time(dto.getEndTime())
                .start_price(dto.getStartPrice())
                .end_price(dto.getEndPrice())
                .shop(shop)
                .build();
    }

    default AuctionDTO entityToDto(Auction auction) {
        return AuctionDTO.builder()
                .auctionId(auction.getAuctionId())
//                .memberId(auction.getMember() != null ? auction.getMember().getMemberId() : null)  // memberId 처리
                .shopId(auction.getShop() != null ? auction.getShop().getShopId() : null)  // shopId 처리
                .shopName(auction.getShop() != null ? auction.getShop().getName() : null)  // shopName 처리
                .shopPrice(auction.getShop() != null ? auction.getShop().getPrice() : null) // shopPrice 처리
                .shopDetail(auction.getShop() != null ? auction.getShop().getDetail() : null)
                .auctionStatus(auction.getAuction_status())
                .startPrice(auction.getStart_price())
                .endPrice(auction.getEnd_price())
                .startTime(auction.getStart_time())
                .endTime(auction.getEnd_time())
                .build();
    }
}
