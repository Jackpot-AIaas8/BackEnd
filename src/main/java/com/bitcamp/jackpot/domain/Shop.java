package com.bitcamp.jackpot.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Shop extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shopId;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String detail;

    @Column(nullable = false)
    private int category;

    @Column(nullable = false)
    private int price;

    private int buy_count;
    private int cell_count;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Review> reviews;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Cart> carts;


}