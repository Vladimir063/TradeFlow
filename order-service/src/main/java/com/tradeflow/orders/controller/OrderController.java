package com.tradeflow.orders.controller;

import com.tradeflow.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.tradeflow.orders.dto.CreatedOrder;
import com.tradeflow.event.OrderDto;
import com.tradeflow.orders.service.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/create")
    public OrderCreatedEvent orderCreated(@RequestBody CreatedOrder createdOrder) {
        return orderService.orderCreated(createdOrder);
    }


    @GetMapping("/user")
    public List<OrderDto> findByUserId(@RequestParam UUID userId) {
        return orderService.findByUserId(userId);
    }

    @GetMapping("/user/{userId}")
    public List<OrderDto> getOrderByUserId(@PathVariable UUID userId) {
        return orderService.getOrderByUserId(userId);
    }
}
