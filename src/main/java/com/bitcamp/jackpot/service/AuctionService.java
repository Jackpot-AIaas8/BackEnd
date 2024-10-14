package com.bitcamp.jackpot.service;

import com.bitcamp.jackpot.domain.Member;
import com.bitcamp.jackpot.domain.Orders;
import com.bitcamp.jackpot.domain.Shop;
import com.bitcamp.jackpot.dto.AuctionDTO;
import com.bitcamp.jackpot.dto.OrdersDTO;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public interface AuctionService {
    @Transactional
    void register(AuctionDTO auctionDTO);

    void edit(OrdersDTO ordersDTO);

    void remove(Integer orderId);

    OrdersDTO findOne(Integer orderId);

    List<OrdersDTO> findAll();

    default Orders dtoToEntity(OrdersDTO ordersDTO) {
        Member member = Member.builder().memberId(ordersDTO.getMemberId()).build();
        Shop shop = Shop.builder().shopId(ordersDTO.getShopId()).build();
        return Orders.builder()
                .orderId(ordersDTO.getOrderId())
                .pay_state(ordersDTO.getPay_state())
                .delivery_state(ordersDTO.getDelivery_state())
                .member(member)
                .shop(shop)
                .build();
    }

    default OrdersDTO entityToDto(Orders orders) {
        return OrdersDTO.builder()
                .orderId(orders.getOrderId())
                .pay_state(orders.getPay_state())
                .delivery_state(orders.getDelivery_state())
                .memberId(orders.getMember() != null ? orders.getMember().getMemberId() : null)  // memberId 처리
                .shopId(orders.getShop() != null ? orders.getShop().getShopId() : null)  // shopId 처리
                .shopName(orders.getShop() != null ? orders.getShop().getName() : null)  // shopName 처리
                .build();
    }

    default List<OrdersDTO> entityListToDtoList(List<Orders> orders) {
        return orders.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }
}
