package com.tradeflow.saga.consumer;

import com.tradeflow.saga.service.SagaService;
import com.tradeflow.event.kafka.Topics;
import com.tradeflow.trade.api.command.TradeExecutedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradeExecutedConsumer {


    private final SagaService sagaService;

    @KafkaListener(topics = Topics.TRADE_EXECUTED)
    public void onTradeExecuted(TradeExecutedEvent event) {
        log.info("Received TradeExecutedEvent: {}", event);
        sagaService.handleTradeExecuted(event);
    }
}
