package com.tradeflow.notifications.consumer;

import com.tradeflow.event.OrderCompletedEvent;
import com.tradeflow.event.kafka.Topics;

import com.tradeflow.notifications.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderCompletedConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = Topics.ORDER_COMPLETED_EVENTS)
    public void onOrderCompleted(OrderCompletedEvent orderCompletedEvent) {
        notificationService.handleOrderCompleted(orderCompletedEvent);
    }
}
