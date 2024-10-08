package com.bitcamp.jackpot.service;

import com.bitcamp.jackpot.domain.Member;
import com.bitcamp.jackpot.domain.Orders;
import com.bitcamp.jackpot.dto.CustomUserDetails;
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
        CustomUserDetails userDetails = getUserDetails();
        Member member = memberRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
        log.info(ordersDTO);
        Orders orders = dtoToEntity(ordersDTO);
        ordersRepository.save(orders);
    }

    @Override
    public void edit(OrdersDTO ordersDTO) {
        log.info(ordersDTO);

        Orders orders = dtoToEntity(ordersDTO);
        ordersRepository.save(orders);
    }

    @Override
    public void remove(Integer orderId) {
        log.info(ordersRepository.findById(orderId));
        ordersRepository.deleteById(orderId);
    }

    @Override
    public OrdersDTO findOne(Integer orderId) {
        Orders orders = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Orders not found"));
        ;
        return entityToDto(orders);
    }

    @Override
    public List<OrdersDTO> findAll() {
        List<OrdersDTO> ordersDTO = entityListToDtoList(ordersRepository.findAll());
        return ordersDTO;
    }
}
