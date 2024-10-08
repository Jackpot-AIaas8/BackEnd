package com.bitcamp.jackpot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DogListDTO {
    private int dogId;

    private String name;

    private String species;

    private int heart;

    private int isHeart;
}
