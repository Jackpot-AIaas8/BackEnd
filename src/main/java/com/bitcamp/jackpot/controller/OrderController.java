package com.bitcamp.jackpot.controller;

import com.bitcamp.jackpot.dto.OrdersDTO;
import com.bitcamp.jackpot.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@Log4j2
@RequiredArgsConstructor
//@CrossOrigin("*")
public class OrderController {

    private final OrdersService ordersService;

    @PostMapping("/register")
    public void register(@RequestBody OrdersDTO ordersDTO) {
        System.out.println("Order ID from Frontend: " + ordersDTO.getOrderId());

        log.info(ordersDTO);
        ordersService.register(ordersDTO);
    }

    @PostMapping("/edit")
    public void edit(@RequestBody OrdersDTO ordersDTO) {
        log.info(ordersDTO);
        ordersService.edit(ordersDTO);
    }

    @GetMapping("/findall")
    public List<OrdersDTO> findAll() {
        return ordersService.findAll();
    }

    @GetMapping("/findone")
    public OrdersDTO findOne(@RequestParam Integer orderId) {
        log.info(orderId);
        OrdersDTO ordersDTO = ordersService.findOne(orderId);
//        log.info(orderDTO);
        return ordersDTO;
    }

    @DeleteMapping("/remove")
    public void remove(@RequestParam Integer orderId) {
        log.info(orderId);
        ordersService.remove(orderId);
    }

}
