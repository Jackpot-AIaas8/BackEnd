package com.bitcamp.jackpot.service;

import com.bitcamp.jackpot.domain.Member;
import com.bitcamp.jackpot.domain.Cart;
import com.bitcamp.jackpot.domain.Shop;
import com.bitcamp.jackpot.dto.CartDTO;

import java.util.List;
import java.util.stream.Collectors;

public interface CartService {
    void register(CartDTO cartDTO);

    void edit(CartDTO cartDTO);

    void remove(Integer cartId);

    //     CartDTO findOne(Integer CartId);
    List<CartDTO> findAll();

    default Cart dtoToEntity(CartDTO cartDTO) {
        Member member = Member.builder().memberId(cartDTO.getMemberId()).build();
        Shop shop = Shop.builder().shopId(cartDTO.getShopId()).build();
        return Cart.builder()
                .cartId(cartDTO.getCartId())
                .member(member)
                .shop(shop)
                .build();
    }

    default CartDTO entityToDto(Cart cart) {
        return CartDTO.builder()
                .cartId(cart.getCartId())
                .memberId(cart.getMember() != null ? cart.getMember().getMemberId() : null)  // memberId 처리
                .shopId(cart.getShop() != null ? cart.getShop().getShopId() : null)  // shopId 처리
                .shopName(cart.getShop() != null ? cart.getShop().getName() : null)  // shopName 처리
                .shopPrice(cart.getShop() != null ? cart.getShop().getPrice() : null) // shopPrice 처리
                .build();
    }

    default List<CartDTO> entityListToDtoList(List<Cart> carts) {
        return carts.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }
}
