package com.bitcamp.jackpot.repository;

import com.bitcamp.jackpot.domain.Dog;
import com.bitcamp.jackpot.domain.Fund;
import com.bitcamp.jackpot.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FundRepository extends JpaRepository<Fund, Integer> {
    List<Fund> findFundByDogId(Dog dogId, Pageable pageable);
    List<Fund> findFundByMemberId(Member memberId, Pageable pageable);
}
