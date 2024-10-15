package com.bitcamp.jackpot.repository;

import com.bitcamp.jackpot.domain.Auction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Integer> {
    @Query(value = "SELECT a FROM Auction a WHERE a.start_time > :currentTime ORDER BY a.start_time ASC")
    List<Auction> findNextAuction(Pageable pageable, @Param("currentTime") LocalDateTime currentTime);
}
