package com.bitcamp.jackpot.repository;

import com.bitcamp.jackpot.domain.Bidding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BiddingRepository extends JpaRepository<Bidding, Integer> {
    //public void deleteAllByAuctionId(int auction_id);
}
