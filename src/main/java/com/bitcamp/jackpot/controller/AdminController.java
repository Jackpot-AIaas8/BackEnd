package com.bitcamp.jackpot.controller;

import com.bitcamp.jackpot.domain.Board;
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
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    @GetMapping("/member/findOne/{email}")
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
        log.info(pageRequestDTO);
        PageResponseDTO<BoardDTO> pageResponseDTO = boardService.findAll(pageRequestDTO);
        log.info(pageResponseDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pageResponseDTO);
        // 보드디티오 리스트 리턴. 그런데 전체 다 리턴하는게 아니라 열개만 리턴
        // 리스폰스엔티티에 페이지리스폰스디티오를 실어보냄.
    }

    @GetMapping("/board/search")
    public PageResponseDTO<BoardDTO> searchBoard(@RequestParam("search") String keyword, PageRequestDTO pageRequestDTO) {
        log.info(keyword);
        PageResponseDTO<BoardDTO> pageResponseDTO = boardService.search(keyword, pageRequestDTO);

        return pageResponseDTO;

    }

    @GetMapping("/board/findOne/{boardId}")
    public ResponseEntity<?> findOne(@PathVariable("boardId") Integer boardId) {
//        int boardId = Integer.valueOf(boardIdStr);
        //패스버라이어블은 스트링으로 받아오기때문에 인트값으로 바꿔줌. 수정 - 바꿔줄 필요 없음 자동으로 바뀜
        BoardDTO boardDTO = boardService.findOne(boardId);
        // 서비스단 메서드를 실행시켜 게시글 객체를 글번호로 검색해서 가져옴
        // 글 번호는 uri에 담아 가져옴

        // 서비스단에서 객체를 제대로 잘 가져왔는가 로그찍어봄.
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(boardDTO);
        // 리스폰스엔티티 객체에 찾은 게시글을 실어 보낸다.
    }

    @DeleteMapping("/board/remove/{boardId}")
    public void remove(@PathVariable Integer boardId) {

//        int boardId = Integer.valueOf(boardIdStr);
//        System.out.println(boardId);
        boardService.remove(boardId);
        // 인트값을 넣어서 리무브 메서드 실행해도 오토박싱되서 실행됨.
        // 게시글 삭제 시 게시글에 달린 댓글들도 삭제되게 해야 함.
    }

    @PostMapping("/board/register")
    public ResponseEntity<String> register(@ModelAttribute Board board, BindingResult bindingResult) throws BindException {
        log.info(bindingResult);
        try {
            boardService.register(board);
            String successPath = "\"status\":200";
            return ResponseEntity.ok(successPath);
            // BoardDTO유효성 검사(낫널, 낫임티 어노테이션)통과하면 트라이문 실행해서 글목록으로 이동함.
        } catch (Exception e) {
            log.error(e);
            String failPath = "\"status\":403";
            return ResponseEntity.ok(failPath);
            // BoardDTO유효성 검사 통과 못해서 익셉션 터지면 다시 글작성 페이지로 돌아감.
            // 근데 작성내용이 유지되는지는 모르겠네. 유지안되면 처음부터 다시 써야함.
        }
    }

}