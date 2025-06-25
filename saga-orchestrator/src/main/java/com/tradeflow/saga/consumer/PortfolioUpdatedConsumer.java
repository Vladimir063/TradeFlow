package com.tradeflow.saga.consumer;

import com.tradeflow.saga.service.SagaService;
import com.tradeflow.event.kafka.Topics;
import com.tradeflow.portfolio.api.command.PortfolioUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PortfolioUpdatedConsumer {

    private final SagaService sagaService;

    @KafkaListener(topics = Topics.PORTFOLIO_EXECUTED)
    public void onPaymentReserved(PortfolioUpdatedEvent event) {
        log.info("Received PortfolioUpdatedEvent {}", event);
        sagaService.handlePortfolioUpdate(event);
    }
}
