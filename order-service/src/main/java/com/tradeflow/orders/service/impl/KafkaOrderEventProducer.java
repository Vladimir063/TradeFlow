package com.tradeflow.orders.service.impl;

import com.tradeflow.event.OrderCompletedEvent;
import com.tradeflow.event.OrderCreatedEvent;
import com.tradeflow.event.kafka.Topics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.tradeflow.orders.service.OrderEventProducer;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaOrderEventProducer implements OrderEventProducer {

    private final KafkaTemplate<UUID, OrderCreatedEvent> kafkaTemplateOrderCreated;
    private final KafkaTemplate<UUID, OrderCompletedEvent> kafkaTemplateOrderCompleted;

    @Override
    public void publishOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        kafkaTemplateOrderCreated.send(Topics.ORDER_CREATE_EVENTS, orderCreatedEvent.orderId(), orderCreatedEvent);
    }

    @Override
    public void publishOrderCompletedEvent(OrderCompletedEvent orderCompletedEvent) {
        kafkaTemplateOrderCompleted.send(Topics.ORDER_COMPLETED_EVENTS, orderCompletedEvent.orderId(), orderCompletedEvent);
    }
}