package com.bitcamp.jackpot.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Fund extends  BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fundId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Dog dogId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member memberId;

//    @ManyToOne
//    @JoinColumn(name = "memberId", referencedColumnName = "memberId")
//    private Member member;

    @Column(nullable = false)
    private int collection;
}
