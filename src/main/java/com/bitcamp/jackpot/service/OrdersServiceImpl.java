package com.bitcamp.jackpot.service;

import com.bitcamp.jackpot.domain.Member;
import com.bitcamp.jackpot.domain.Orders;
import com.bitcamp.jackpot.domain.Shop;
import com.bitcamp.jackpot.jwt.CustomUserDetails;

import com.bitcamp.jackpot.dto.OrdersDTO;
import com.bitcamp.jackpot.repository.MemberRepository;
import com.bitcamp.jackpot.repository.OrdersRepository;
import com.bitcamp.jackpot.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final ShopRepository shopRepository;

    @Override
    public void register(OrdersDTO ordersDTO) {
        log.info("Starting order registration for OrderDTO: {}", ordersDTO);

        // Shop과 Member 엔티티 조회
        Shop shop = shopRepository.findById(ordersDTO.getShopId())
                .orElseThrow(() -> {
                    log.error("Shop not found for shopId: {}", ordersDTO.getShopId());
                    return new RuntimeException("Shop not found");
                });

        Member member = memberRepository.findById(ordersDTO.getMemberID())
                .orElseThrow(() -> {
                    log.error("Member not found for memberId: {}", ordersDTO.getMemberID());
                    return new RuntimeException("Member not found");
                });

        // Orders 엔티티로 변환
        Orders order = new Orders();
        order.setOrderId(ordersDTO.getOrderId());
        order.setShop(shop);
        order.setMember(member);
        order.setDelivery_state(0);  // 초기 배송 상태 설정
        order.setQuantity(ordersDTO.getQuantity());  // 상품 수량 저장

        // 주문을 저장
        log.info("Saving order to database: {}", order);
        ordersRepository.save(order);

        log.info("Order registered successfully with orderId: {}", ordersDTO.getOrderId());
    }

    @Override
    public void edit(OrdersDTO ordersDTO) {
        log.info("OrdersDTO for Edit: {}", ordersDTO);

        Orders orders = dtoToEntity(ordersDTO);
        ordersRepository.save(orders);
    }

    @Override
    public OrdersDTO findOne(Integer id) {
        Orders orders = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orders not found"));
        return entityToDto(orders);
    }

    @Override
    public OrdersDTO findOneByOrderId(Integer memberId) {
        return null;
    }

    @Override
    public List<OrdersDTO> findAll() {
        List<OrdersDTO> ordersDTOList = entityListToDtoList(ordersRepository.findAll());
        return ordersDTOList;
    }

    // 주문을 orderId로 조회하는 메서드 추가 (orderId는 String)
    public OrdersDTO findOneByOrderId(String orderId) {
        Orders orders = ordersRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with orderId: " + orderId));
        return entityToDto(orders);
    }
}
