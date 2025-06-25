package com.tradeflow.orders.controller;

import com.tradeflow.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.tradeflow.orders.dto.CreatedOrder;
import com.tradeflow.orders.dto.OrderDto;
import com.tradeflow.orders.service.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
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

    @GetMapping()
    public OrderDto findByOrder(@RequestParam UUID orderId) {
        return orderService.getOrderDtoById(orderId);
    }
}
