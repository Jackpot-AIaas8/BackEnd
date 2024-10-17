package com.bitcamp.jackpot.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"shop", "member"})
public class Auction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int auctionId;
    @Column(name = "auction_status")
    private int auctionStatus;
    @NotNull
    @Column(name = "start_time")
    private LocalDateTime startTime;

    private LocalDateTime end_time;
    @NotNull
    private int start_price;

    private int end_price;

    @ManyToOne
    @JoinColumn(name = "shopId", referencedColumnName = "shopId")
    @JsonBackReference
    Shop shop;
    @ManyToOne
    @JoinColumn(name = "memberId", referencedColumnName = "memberId")
    @JsonBackReference
    Member member;

    public void setAuctionStatus(int auction_status) {
        this.auctionStatus = auction_status;
    }
}
