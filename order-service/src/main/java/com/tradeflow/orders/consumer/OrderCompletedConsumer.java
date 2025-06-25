package com.tradeflow.orders.consumer;

import com.tradeflow.event.CompleteOrderCommand;
import com.tradeflow.event.OrderCompletedEvent;
import com.tradeflow.event.kafka.Topics;
import com.tradeflow.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCompletedConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = Topics.ORDER_COMMANDS)
    public void onOrderCreated(CompleteOrderCommand completeOrderCommand) {
        orderService.orderCompleted(completeOrderCommand);
    }
}
