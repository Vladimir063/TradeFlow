package com.tradeflow.funds.consumer;


import com.tradeflow.event.OrderCompletedEvent;
import com.tradeflow.event.kafka.Topics;
import com.tradeflow.funds.service.ReserveFundsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderCompletedConsumer {

    private final ReserveFundsService reserveFundsService;

    public OrderCompletedConsumer(ReserveFundsService reserveFundsService) {
        this.reserveFundsService = reserveFundsService;
    }

    @KafkaListener(topics = Topics.ORDER_COMPLETED_EVENTS)
    public void onOrderCompleted(OrderCompletedEvent orderCompletedEvent) {
        log.info("Received order completed event: {}", orderCompletedEvent);
        reserveFundsService.handleOrderCompletedEvent(orderCompletedEvent);
    }
}
