package com.bitcamp.jackpot.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Auction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int auctionId;
    @NonNull
    private LocalDateTime start_time;
    @NonNull
    private LocalDateTime end_time;
    @NonNull
    private int start_price;
    @NonNull
    private int end_price;

    @ManyToOne
    @JoinColumn(name = "shopId", referencedColumnName = "shopId")
    Shop shop;
    @ManyToOne
    @JoinColumn(name = "memberId", referencedColumnName = "memberId")
    Member member;
}
