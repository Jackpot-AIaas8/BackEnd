package com.bitcamp.jackpot.repository;

import com.bitcamp.jackpot.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    Optional<Orders> findByOrderId(String orderId);

    @Query("SELECT o FROM Orders o WHERE o.member.memberId = :memberId")
    List<Orders> findByMemberId(int memberId);
}
