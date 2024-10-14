package com.bitcamp.jackpot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuctionDTO {
    private Integer auctionId;
    private int startPrice;
    private int endPrice;
    private int shopId;
    private int memberId;
    private String shopName;
}
