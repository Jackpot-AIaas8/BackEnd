package com.bitcamp.jackpot.repository;

import com.bitcamp.jackpot.domain.Dog;
import com.bitcamp.jackpot.domain.Fund;
import com.bitcamp.jackpot.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FundRepository extends JpaRepository<Fund, Integer> {
    List<Fund> findFundByDogId(Dog dogId, Pageable pageable);

    List<Fund> findFundByMemberId(Member memberId, Pageable pageable);


//    @Query("SELECT f FROM Fund f WHERE f.member.memberId = :memberId")
//    List<Fund> findByMemberId(int memberId);

//    @Query("SELECT f FROM Fund f WHERE f.member.memberId = :memberId")
//    List<Fund> findFundByMemberId(@Param("memberId") int memberId, Pageable pageable);

}
