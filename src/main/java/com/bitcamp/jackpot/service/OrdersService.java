package com.bitcamp.jackpot.service;

import com.bitcamp.jackpot.domain.Member;
import com.bitcamp.jackpot.domain.Orders;
import com.bitcamp.jackpot.domain.Shop;
import com.bitcamp.jackpot.dto.OrdersDTO;
import com.bitcamp.jackpot.dto.ProductDTO;

import java.util.List;
import java.util.stream.Collectors;

public interface OrdersService {

    void register(OrdersDTO ordersDTO);

    void edit(OrdersDTO ordersDTO);

    // 기본키인 id를 기준으로 삭제
    void remove(Integer id);

    // 기본키인 id로 조회
    OrdersDTO findOne(Integer id);

    OrdersDTO findOneByOrderId(String orderId);

    List<OrdersDTO> findAll();

    // DTO -> Entity 변환 메서드 (orderId는 String으로, id는 DB에서 자동 생성)
    default Orders dtoToEntity(OrdersDTO ordersDTO) {
        Member member = Member.builder().name(ordersDTO.getName()).build();

        // 주문의 상품 리스트 처리 (ProductDTO 리스트 -> Shop 엔티티 리스트)
        List<Shop> shopList = ordersDTO.getProducts().stream()
                .map(productDTO -> Shop.builder()
                        .name(productDTO.getShopName())
                        .shopId(productDTO.getProductPrice())  // shopId를 적절히 변경해야 할 수도 있음
                        .build())
                .collect(Collectors.toList());

        return Orders.builder()
                .orderId(ordersDTO.getOrderId())  // DTO에서 엔티티로 매핑 확인
                .delivery_state(ordersDTO.getDeliveryState())  // 배송 상태
                .member(member)  // Member 엔티티
                .shop(shopList.get(0))  // 첫 번째 상품으로 예시, 여러 상품의 경우 다르게 처리 필요
                .build();
    }

    // Entity -> DTO 변환 메서드 (orderId는 String으로, id는 DB에서 자동 생성된 값)
    default OrdersDTO entityToDto(Orders orders) {
        // Shop 엔티티를 ProductDTO로 변환
        List<ProductDTO> products = List.of(
                ProductDTO.builder()
                        .shopName(orders.getShop().getName())
                        .productPrice(orders.getShop().getPrice())  // 적절히 가격 매핑 필요
                        .quantity(1)  // 상품 수량 처리 필요
                        .build()
        );

        return OrdersDTO.builder()
                .orderId(orders.getOrderId())  // 주문 id는 String
                .deliveryState(orders.getDelivery_state())
                .name(orders.getMember().getName())
                .phone(orders.getMember().getPhone())
                .address(orders.getMember().getAddress())
                .products(products)  // 상품 리스트
                .build();
    }

    // Entity 리스트 -> DTO 리스트 변환 메서드
    default List<OrdersDTO> entityListToDtoList(List<Orders> orders) {
        return orders.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }
}
