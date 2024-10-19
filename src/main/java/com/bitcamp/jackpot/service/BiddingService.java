package com.bitcamp.jackpot.service;

import com.bitcamp.jackpot.domain.Auction;
import com.bitcamp.jackpot.domain.Bidding;
import com.bitcamp.jackpot.domain.Member;
import com.bitcamp.jackpot.domain.Shop;
import com.bitcamp.jackpot.dto.AuctionDTO;
import com.bitcamp.jackpot.dto.BiddingDTO;
import com.bitcamp.jackpot.dto.PageRequestDTO;
import com.bitcamp.jackpot.dto.PageResponseDTO;

public interface BiddingService {

    void register(BiddingDTO biddingDTO);

    //void remove(int auctionId);

    BiddingDTO findBeforeBidding(int biddingId);

    default Bidding dtoToEntity(BiddingDTO dto, Auction auction, Member member) {
        return Bidding.builder()
//                .auctionId(dto.getAuctionId())
                .bid_price(dto.getBidPrice())
                .bid_time(dto.getBidTime())
                .member(member)
                .auction(auction)
                .build();
    }

    default BiddingDTO entityToDto(Bidding bidding) {
        return BiddingDTO.builder()
                .biddingId(bidding.getBiddingId())
                .bidTime(bidding.getBid_time())
                .bidPrice(bidding.getBid_price())
                .memberId(bidding.getMember() != null ? bidding.getMember().getMemberId() : null)
                .memberName(bidding.getMember() != null ? bidding.getMember().getName() : null)
                .auctionId(bidding.getAuction() != null ? bidding.getAuction().getAuctionId() : null)
                .build();
    }
}
