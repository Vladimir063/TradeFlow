package com.tradeflow.portfolio.service;

import com.tradeflow.portfolio.api.command.ReserveStockCommand;
import com.tradeflow.portfolio.api.command.UpdatePortfolioCommand;

public interface PortfolioService {

    void handleUpdatePortfolioCommand(UpdatePortfolioCommand updatePortfolioCommand);

    void handleReserveStockCommand(ReserveStockCommand reserveStockCommand);
}
