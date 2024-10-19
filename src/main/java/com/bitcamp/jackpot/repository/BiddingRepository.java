package com.bitcamp.jackpot.repository;

import com.bitcamp.jackpot.domain.Bidding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BiddingRepository extends JpaRepository<Bidding, Integer> {
    void deleteAllByAuction_AuctionId(int auctionId);
}
