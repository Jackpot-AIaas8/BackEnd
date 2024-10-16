package com.bitcamp.jackpot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Orders extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "orderId", nullable = false)
    private String orderId;

    private int delivery_state;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    Shop shop;
    @ManyToOne
    @JoinColumn(name = "memberId", referencedColumnName = "memberId")
    Member member;

}
