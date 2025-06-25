package com.tradeflow.orders.service;


import com.tradeflow.event.OrderCompletedEvent;
import com.tradeflow.event.OrderCreatedEvent;

public interface OrderEventProducer {

    void publishOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent);

    void publishOrderCompletedEvent(OrderCompletedEvent orderCompletedEvent);
}
