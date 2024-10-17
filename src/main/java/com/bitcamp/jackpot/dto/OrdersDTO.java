package com.bitcamp.jackpot.dto;

import lombok.*;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrdersDTO {

    private int id;
    private String orderId;            // 주문 ID
    private String name;               // 구매자 이름
    private String phone;              // 전화번호
    private String address;            // 배송지 주소
    private int deliveryState;         // 배송 상태
    private int totalPrice;
    private int memberID;              // 회원 ID 추가
    private List<ProductDTO> products; // 상품 리스트
}
