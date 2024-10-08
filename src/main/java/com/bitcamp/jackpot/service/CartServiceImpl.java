package com.bitcamp.jackpot.service;

import com.bitcamp.jackpot.domain.Cart;
import com.bitcamp.jackpot.dto.CartDTO;
import com.bitcamp.jackpot.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    @Override
    public void register(CartDTO cartDTO) {
        log.info(cartDTO);
        Cart cart = dtoToEntity(cartDTO);

        cartRepository.save(cart);
    }

    @Override
    public void edit(CartDTO cartDTO) {
//        log.info(CartDTO);
        Cart cart = dtoToEntity(cartDTO);
//        Cart cart = cartRepository.findById(cartDTO.getCartId())
//                .orElseThrow(() -> new RuntimeException("Cart not found"));
//        Cart.updateFromDTO(cartDTO);

        cartRepository.save(cart);
    }

    @Override
    public void remove(Integer cartId) {
        log.info(cartRepository.findById(cartId));
        cartRepository.deleteById(cartId);
    }

//    @Override
//    public CartDTO findOne(Integer cartId) {
//        Cart Cart = cartRepository.findById(cartId)
//                .orElseThrow(() -> new RuntimeException("Cart not found")); ;
//        return entityToDto(Cart);
//    }

    @Override
    public List<CartDTO> findAll() {
        List<CartDTO> cartDTO = entityListToDtoList(cartRepository.findAll());
        return cartDTO;
    }
}
