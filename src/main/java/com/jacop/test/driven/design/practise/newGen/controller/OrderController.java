package com.jacop.test.driven.design.practise.newGen.controller;

import com.jacop.test.driven.design.practise.newGen.OrderService;
import com.jacop.test.driven.design.practise.newGen.model.PlaceOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/place")
    public void placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest) {
         orderService.placeOrder(placeOrderRequest);
    }
}
