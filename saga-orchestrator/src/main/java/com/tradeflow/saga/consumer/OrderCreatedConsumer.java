package com.tradeflow.saga.consumer;

import com.tradeflow.saga.service.SagaService;
import com.tradeflow.event.OrderCreatedEvent;
import com.tradeflow.event.kafka.Topics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedConsumer {

    private final SagaService sagaService;

    @KafkaListener(topics = Topics.ORDER_CREATE_EVENTS)
    public void onOrderCreated(OrderCreatedEvent orderCreatedEvent) {
        log.warn("Received order created event: {}", orderCreatedEvent);
        sagaService.handleOrderCreated(orderCreatedEvent);
    }
}
