package com.bitcamp.jackpot.service;

import com.bitcamp.jackpot.domain.Auction;
import com.bitcamp.jackpot.domain.Bidding;
import com.bitcamp.jackpot.domain.Member;
import com.bitcamp.jackpot.dto.BiddingDTO;
import com.bitcamp.jackpot.jwt.CustomUserDetails;
import com.bitcamp.jackpot.repository.AuctionRepository;
import com.bitcamp.jackpot.repository.BiddingRepository;
import com.bitcamp.jackpot.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class BiddingServiceImpl implements BiddingService {
    private final BiddingRepository biddingRepository;
    private final MemberRepository memberRepository;
    private final AuctionRepository auctionRepository;

    private CustomUserDetails getUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (CustomUserDetails) auth.getPrincipal();
    }

    public void register(BiddingDTO biddingDTO) {
        CustomUserDetails userDetails = getUserDetails();
        Member member = memberRepository.findById(biddingDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
        Auction auction = auctionRepository.findById(biddingDTO.getAuctionId())
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다"));
        Bidding bidding = dtoToEntity(biddingDTO, auction, member);
        biddingRepository.save(bidding);
    }

//    public void remove(int auctionId) {
//        biddingRepository.deleteAllByAuctionId(auctionId);
//    }

    public BiddingDTO findBeforeBidding(int biddingId) {
        return null;
    }


}
