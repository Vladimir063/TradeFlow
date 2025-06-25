package com.tradeflow.portfolio.consumer;

import com.tradeflow.event.kafka.Topics;
import com.tradeflow.portfolio.api.command.ReserveStockCommand;
import com.tradeflow.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReserveStockConsumer {

    private final PortfolioService portfolioService;

    @KafkaListener(topics = Topics.PORTFOLIO_COMMANDS_RESERVE)
    public void onReserveStock(ReserveStockCommand reserveStockCommand) {
        portfolioService.handleReserveStockCommand(reserveStockCommand);
    }
}
