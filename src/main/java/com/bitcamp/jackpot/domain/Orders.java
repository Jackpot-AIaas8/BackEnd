package com.bitcamp.jackpot.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Orders extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    private int delivery_state;

    @ManyToOne
    @JoinColumn(name = "shopId", referencedColumnName = "shopId")
    Shop shop;
    @ManyToOne
    @JoinColumn(name = "memberId", referencedColumnName = "memberId")
    Member member;
   
}
