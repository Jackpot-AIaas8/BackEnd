package com.bitcamp.jackpot.controller;

import com.bitcamp.jackpot.domain.Cart;
import com.bitcamp.jackpot.dto.CartDTO;
import com.bitcamp.jackpot.repository.MemberRepository;
import com.bitcamp.jackpot.repository.ShopRepository;
import com.bitcamp.jackpot.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@Log4j2
@RequiredArgsConstructor
//@CrossOrigin("*")
public class CartController {

    private final CartService cartService;

    @PostMapping("/register")
    public void register(@RequestBody CartDTO cartDTO) {
        log.info(cartDTO);
        cartService.register(cartDTO);
    }

    @PostMapping("/edit")
    public void edit(@RequestBody CartDTO cartDTO) {
        log.info(cartDTO);
        cartService.edit(cartDTO);
    }

    @GetMapping("/findAll")
    public List<CartDTO> findAll() {
        List<CartDTO> result= cartService.findAll();
        log.info(result);
        return result;
    }

//    @GetMapping("/findone")
//    public CartDTO findOne(@RequestParam Integer cartId) {
//        log.info(cartId);
//        CartDTO cartDTO = cartService.findOne(cartId);
//        log.info(cartDTO);
//        return cartDTO;
//    }

    @DeleteMapping("/remove")
    public void remove(@RequestParam Integer cartId) {
        log.info(cartId);
        cartService.remove(cartId);
    }

}
