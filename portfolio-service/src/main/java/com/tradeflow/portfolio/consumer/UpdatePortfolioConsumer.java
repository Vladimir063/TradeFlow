package com.tradeflow.portfolio.consumer;

import com.tradeflow.portfolio.service.PortfolioService;
import com.tradeflow.event.kafka.Topics;
import com.tradeflow.portfolio.api.command.UpdatePortfolioCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePortfolioConsumer {

    private final PortfolioService portfolioService;

    @KafkaListener(topics = Topics.PORTFOLIO_COMMANDS_UPDATE)
    public void onUpdatePortfolio(UpdatePortfolioCommand updatePortfolioCommand) {
        portfolioService.handleUpdatePortfolioCommand(updatePortfolioCommand);
    }
}
