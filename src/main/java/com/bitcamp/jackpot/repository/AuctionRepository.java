package com.bitcamp.jackpot.repository;

import com.bitcamp.jackpot.domain.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Integer> {
}
