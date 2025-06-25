package com.tradeflow.saga.consumer;

import com.tradeflow.event.kafka.Topics;
import com.tradeflow.portfolio.api.command.PortfolioReservedStockEvent;
import com.tradeflow.saga.service.SagaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReserveStockConsumer {

    private final SagaService sagaService;

    @KafkaListener(topics = Topics.PORTFOLIO_RESERVED_EVENTS)
    public void onPortfolioReserveEvent(PortfolioReservedStockEvent event) {
        log.info("Received PortfolioReservedStockEvent: {}", event);
        sagaService.handlePortfolioReserved(event);
    }


}
