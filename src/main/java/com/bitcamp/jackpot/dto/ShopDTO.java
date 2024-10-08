package com.bitcamp.jackpot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopDTO {
    private int shopId;
    private String name;
    private int category;
    private String price;
    private String detail;
    private int buy_count;
    private int cell_count;
}
