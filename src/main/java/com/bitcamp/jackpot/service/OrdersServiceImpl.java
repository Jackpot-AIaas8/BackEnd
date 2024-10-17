package com.bitcamp.jackpot.service;

import com.bitcamp.jackpot.domain.Orders;
import com.bitcamp.jackpot.jwt.CustomUserDetails;
import com.bitcamp.jackpot.dto.OrdersDTO;
import com.bitcamp.jackpot.repository.MemberRepository;
import com.bitcamp.jackpot.repository.OrdersRepository;
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

    // 로그인된 사용자 정보를 가져오는 메서드
    private CustomUserDetails getUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (CustomUserDetails) auth.getPrincipal();
    }

    @Override
    public void register(OrdersDTO ordersDTO) {
        // 사용자 인증 없이 처리하는 로직
        log.info("OrdersDTO: {}", ordersDTO);


        // 주문 정보를 저장 (사용자 정보가 필요하지 않다면 생략)
        Orders orders = dtoToEntity(ordersDTO);
        // 필요한 경우 기본값이나 다른 방식으로 member를 설정하거나 아예 member 설정을 하지 않음
        ordersRepository.save(orders);
        log.info("Orders Entity: {}", orders);

        if (ordersDTO.getOrderId() == null || ordersDTO.getOrderId().isEmpty()) {
            throw new RuntimeException("OrderId is required");

        }

    }

    @Override
    public void edit(OrdersDTO ordersDTO) {
        log.info("OrdersDTO for Edit: {}", ordersDTO);

        Orders orders = dtoToEntity(ordersDTO);
        ordersRepository.save(orders);
    }

    @Override
    public void remove(Integer id) {
        log.info("Removing order with ID: {}", id);
        ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orders not found"));
        ordersRepository.deleteById(id);
    }

    @Override
    public OrdersDTO findOne(Integer id) {
        Orders orders = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orders not found"));
        return entityToDto(orders);
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
