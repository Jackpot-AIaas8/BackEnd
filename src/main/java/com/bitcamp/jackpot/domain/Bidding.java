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
public class Bidding extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int biddingId;
    @NonNull
    private int bid_price;
    @NonNull
    private LocalDateTime bid_time;

    @ManyToOne
    Auction auction;
    @ManyToOne
    Member member;
}
