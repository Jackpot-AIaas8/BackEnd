package com.bitcamp.jackpot.repository;

import com.bitcamp.jackpot.domain.Orders;
import com.bitcamp.jackpot.domain.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    @Query("SELECT o FROM Orders o WHERE o.member.memberId = :memberId")
    Page<Orders> findByMemberId(@Param("memberId") int memberId, Pageable pageable);

}
