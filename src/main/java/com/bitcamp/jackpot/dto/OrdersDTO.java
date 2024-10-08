package com.bitcamp.jackpot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDTO {
    private Integer orderId;
    private int pay_state;
    private int delivery_state;
    private int shopId;
    private int memberId;
    private String shopName;
    private int stockId;
    private int cellCount;
}
