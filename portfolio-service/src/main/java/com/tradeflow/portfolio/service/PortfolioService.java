package com.tradeflow.portfolio.service;

import com.tradeflow.portfolio.api.command.PortfolioDto;
import com.tradeflow.portfolio.api.command.ReserveStockCommand;
import com.tradeflow.portfolio.api.command.UpdatePortfolioCommand;

import java.util.UUID;

public interface PortfolioService {

    void handleUpdatePortfolioCommand(UpdatePortfolioCommand updatePortfolioCommand);

    void handleReserveStockCommand(ReserveStockCommand reserveStockCommand);

    PortfolioDto getPortfolio(UUID userId);
}
