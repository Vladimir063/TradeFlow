package com.tradeflow.orders.service;


import com.tradeflow.event.CompleteOrderCommand;
import com.tradeflow.orders.dto.CreatedOrder;
import com.tradeflow.orders.dto.OrderDto;
import com.tradeflow.event.OrderCreatedEvent;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderCreatedEvent orderCreated(CreatedOrder order);

    List<OrderDto> findByUserId(UUID userId);

    OrderDto getOrderDtoById(UUID orderId);

    void orderCompleted(CompleteOrderCommand completeOrderCommand);
}
