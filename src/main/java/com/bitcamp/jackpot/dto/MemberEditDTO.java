package com.bitcamp.jackpot.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberEditDTO {




    @Size(min = 1, max = 30, message = "이름은 최대 30자까지 입력 가능합니다.")
    private String name;

    private String pwd;

    @Size(max = 30, message = "닉네임은 최대 30자까지 입력 가능합니다.")
    private String nickname;

    private String phone;

    private String address;
}