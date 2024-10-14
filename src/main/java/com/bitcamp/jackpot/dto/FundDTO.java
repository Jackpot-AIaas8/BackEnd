package com.bitcamp.jackpot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FundDTO {
    private int fundId;

    private int dogId;
    private int memberId;

    private int collection;
}
