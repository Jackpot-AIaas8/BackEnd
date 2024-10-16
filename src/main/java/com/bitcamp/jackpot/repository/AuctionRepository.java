package com.bitcamp.jackpot.repository;

import com.bitcamp.jackpot.domain.Auction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Integer> {
//    @Query(value = "SELECT a FROM Auction a WHERE a.start_time > :currentTime ORDER BY a.start_time ASC")
////    List<Auction> findNextAuction(Pageable pageable, @Param("currentTime") LocalDateTime currentTime);

//    Auction findFirstByStartTimeGreaterThanAndAuctionStatusNot(LocalDateTime start_time, int auction_status);

    //    @Query("SELECT a FROM Auction a WHERE a.startTime > CURRENT_TIMESTAMP AND a.auctionStatus <> 2")
//    Auction findFirstByStartTimeAfterAndAuctionStatusNot(LocalDateTime startTime, int auctionStatus);

    @Query(value = "SELECT * FROM auction WHERE start_time > :now AND auction_status != :status ORDER BY start_time ASC LIMIT 1", nativeQuery = true)
    Auction findUpcomingAuctionNative(@Param("now") LocalDateTime now, @Param("status") int status);

    Auction findFirstByStartTimeAfterAndAuctionStatus(LocalDateTime start_time, int auction_status);
}
