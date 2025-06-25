package com.tradeflow.execution.consumer;

import com.tradeflow.execution.services.ExecuteTradeService;
import com.tradeflow.event.OrderCreatedEvent;
import com.tradeflow.event.kafka.Topics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderCreateConsumer {

    private final ExecuteTradeService executeTradeService;

    @KafkaListener(topics = Topics.ORDER_CREATE_EVENTS)
    public void onOrderCreated(OrderCreatedEvent orderCreatedEvent) {
        log.warn("Received order created event: {}", orderCreatedEvent);
        executeTradeService.handleOrderCreatedEvent(orderCreatedEvent);
    }
}
