package com.bitcamp.jackpot.controller;

import com.bitcamp.jackpot.domain.Member;
import com.bitcamp.jackpot.dto.*;

import com.bitcamp.jackpot.service.BoardService;
import com.bitcamp.jackpot.service.DogService;
import com.bitcamp.jackpot.service.MemberService;
import com.bitcamp.jackpot.service.ShopService;
import lombok.NonNull;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final ShopService shopService;
    private final DogService dogService;
    private final MemberService memberService;
    private final BoardService boardService;

    //shop
    @PostMapping("/shop/register")
    public void register(@RequestBody ShopDTO shopDTO) {
        shopService.register(shopDTO);
    }

    @PutMapping("/shop/edit/{shopId}")
    public void edit(@PathVariable("shopId") int shopId, @RequestBody ShopDTO shopDTO) {
        log.info(shopDTO);
        shopService.edit(shopId, shopDTO);
    }

    @DeleteMapping("/shop/remove/{shopId}")
    public void shopRemove(@PathVariable("shopId") int shopId) {
        shopService.remove(shopId);
    }

    @GetMapping("/shop/findOne/{shopId}")
    public ShopDTO shopFindOne(@PathVariable("shopId") int shopId) {
        return shopService.findOne(shopId);
    }


    @GetMapping("/shop/findList")
    public PageResponseDTO<ShopDTO> findList(PageRequestDTO pageRequestDTO) {
        return shopService.findList(pageRequestDTO);
    }

    @GetMapping("/shop/search/{searchText}")
    public PageResponseDTO<ShopDTO> search(@PathVariable("searchText") String searchText, PageRequestDTO pageRequestDTO) {
        return shopService.search(searchText, pageRequestDTO);
    }

    @PostMapping("/shop/buycount/{shopId}")
    public void addBuyCount(@PathVariable("shopId") int shopId, @RequestBody int buyCount) {
        shopService.addBuyCount(shopId, buyCount);
    }
    //end shop

    //dog
    @PostMapping("/dog/edit")
    public void dogEdit(@RequestBody DogDTO dogDTO) {
        log.info(dogDTO);
        dogService.edit(dogDTO);
    }

    @DeleteMapping("/dog/remove/{dogId}")
    public void dogRemove(@PathVariable("dogId") int dogId) {
        log.info(dogId);
        dogService.remove(dogId);
    }

    @GetMapping("/dog/findOne/{dogId}")
    public DogResponseDTO dogFindOne(@PathVariable("dogId") int dogId) {
        return dogService.findOne(dogId);
    }

    @GetMapping("/dog/dogList")
    public Map<String, Object> dogList(@RequestParam int page, int size, String sort) {
        Map<String, Object> response = new HashMap<>();

        DogListRequestDTO dogListRequestDTO = DogListRequestDTO.builder()
                .page(page)
                .size(size)
                .sort(sort)
                .build();

        response.put("totalDogNum", dogService.getTotalDogNum());
        response.put("dogList", dogService.dogList(dogListRequestDTO));

        return response;
    }
    //end dog

    //member
    @NonNull
    @GetMapping("/member/findAll")
    public ResponseEntity<Page<MemberDTO>> getAllMembers(@RequestParam(defaultValue = "0") int page, @RequestParam int size) {
        Page<MemberDTO> members = memberService.findAll(page, size);

        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @PostMapping("/member/findOne/{email}")
    public ResponseEntity<MemberDTO> getMember(@PathVariable("email") String email) {
        try {
            MemberDTO memberDTO = memberService.findOne(email);
            return ResponseEntity.ok(memberDTO);  // 성공 시, OK 상태와 함께 MemberDTO 반환
        } catch (
                NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/member/remove/{memberId}")
    public void memberRemove(@PathVariable("memberId") int memberId) {
        log.info(memberId);
        memberService.adminRemove(memberId);
    }

    @PutMapping("/member/edit/{memberId}")
    public void memberEdit(@PathVariable("memberId") int memberId, @RequestBody MemberDTO memberDTO) {
        log.info(memberDTO);
        memberService.adminEdit(memberId, memberDTO);
    }

    //board
    @GetMapping("/board/findAll")
    public ResponseEntity<PageResponseDTO<BoardDTO>> findAll(PageRequestDTO pageRequestDTO) {
        PageResponseDTO<BoardDTO> pageResponseDTO = boardService.findAll(pageRequestDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pageResponseDTO);
        // 보드디티오 리스트 리턴. 그런데 전체 다 리턴하는게 아니라 열개만 리턴
        // 리스폰스엔티티에 페이지리스폰스디티오를 실어보냄.
    }


}