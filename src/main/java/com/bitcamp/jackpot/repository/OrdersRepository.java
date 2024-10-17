package com.bitcamp.jackpot.repository;

import com.bitcamp.jackpot.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    Optional<Orders> findByOrderId(String orderId);

}
