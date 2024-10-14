package com.bitcamp.jackpot.service;

import com.bitcamp.jackpot.domain.Auction;
import com.bitcamp.jackpot.domain.Member;
import com.bitcamp.jackpot.domain.Shop;
import com.bitcamp.jackpot.dto.AuctionDTO;
import com.bitcamp.jackpot.dto.CustomUserDetails;
import com.bitcamp.jackpot.dto.ShopDTO;
import com.bitcamp.jackpot.repository.AuctionRepository;
import com.bitcamp.jackpot.repository.MemberRepository;
import com.bitcamp.jackpot.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuctionServiceImpl implements AuctionService {
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final AuctionRepository auctionRepository;
    private final ShopRepository shopRepository;

    // 로그인된 사용자 정보를 가져오는 메서드
    private CustomUserDetails getUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (CustomUserDetails) auth.getPrincipal();
    }

    @Override
    public void register(AuctionDTO auctionDTO, int shopId) {
        CustomUserDetails userDetails = getUserDetails();
        Member member = memberRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다"));
        Auction auction = dtoToEntity(auctionDTO, shop);
        auctionRepository.save(auction);
    }

    @Override
    public void edit(AuctionDTO auctionDTO) {
        CustomUserDetails userDetails = getUserDetails();
        Member member = memberRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
        Auction auction = modelMapper.map(auctionDTO, Auction.class);
        auctionRepository.save(auction);
    }

    @Override
    public void remove(Integer auctionId) {
        CustomUserDetails userDetails = getUserDetails();
        Member member = memberRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
        auctionRepository.deleteById(auctionId);
    }

    @Override
    public AuctionDTO findOne(Integer auctionId) {
        CustomUserDetails userDetails = getUserDetails();
        Member member = memberRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new RuntimeException("auction not found"));
        AuctionDTO auctionDTO = modelMapper.map(auction, AuctionDTO.class);
        return auctionDTO;
    }

    @Override
    public List<AuctionDTO> findAll() {
        CustomUserDetails userDetails = getUserDetails();
        Member member = memberRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
        List<Auction> auction = auctionRepository.findAll();
        List<AuctionDTO> auctionDTOList = Collections.singletonList(modelMapper.map(auction, AuctionDTO.class));
        return auctionDTOList;
    }
}
