package com.bitcamp.jackpot.service;

import com.bitcamp.jackpot.domain.Member;
import com.bitcamp.jackpot.domain.Orders;
import com.bitcamp.jackpot.domain.Shop;
//import com.bitcamp.jackpot.dto.OrdersDTO;

import java.util.List;
import java.util.stream.Collectors;

public interface OrdersService {
//    void register(OrdersDTO ordersDTO);
//
//    void edit(OrdersDTO ordersDTO);
//
//    void remove(Integer orderId);
//
//    OrdersDTO findOne(Integer orderId);
//
//    List<OrdersDTO> findAll();
//
//    default Orders dtoToEntity(OrdersDTO ordersDTO) {
//        Member member = Member.builder().name(ordersDTO.getName()).build();
//        Shop shop = Shop.builder().shopId(ordersDTO.getOrderId()).build();
//        return Orders.builder()
//                .orderId(ordersDTO.getOrderId())
//                .amount(ordersDTO.getAmount())
//                .delivery_state(ordersDTO.getDelivery_state())
//                .member(member)
//                .shop(shop)
//                .address(ordersDTO.getAddress())
//                .phoneNumber(ordersDTO.getPhone())
//                .build();
//    }
//
//    default OrdersDTO entityToDto(Orders orders) {
//        return OrdersDTO.builder()
//                .orderId(orders.getOrderId())
//                .delivery_state(orders.getDelivery_state())
//                .memberId(orders.getMember() != null ? orders.getMember().getMemberId() : null)  // memberId 처리
//                .OrderId(orders.getOder() != null ? orders.getOrder().getOrderId() : null)  // orderId 처리
//                .shopName(orders.getShop() != null ? orders.getShop().getName() : null)  // shopName 처리
//                .build();
//    }
//
//    default List<OrdersDTO> entityListToDtoList(List<Orders> orders) {
//        return orders.stream()
//                .map(this::entityToDto)
//                .collect(Collectors.toList());
//    }
}
