package com.bitcamp.jackpot.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

@RedisHash
@Getter
@Builder
@Accessors
@NoArgsConstructor
@AllArgsConstructor
public class Blacklist {

    @Id
    private String id;


}
