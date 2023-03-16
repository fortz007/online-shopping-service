package com.fortuneprogramming.orderservice.controllers;

import com.fortuneprogramming.orderservice.dtos.OrderRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @PostMapping
    public String placeOrder(@RequestBody OrderRequestDto orderRequestDto){
        return "Order created successfully";
    }

}
