package com.tradeflow.portfolio.mapper;

import com.tradeflow.portfolio.api.command.PortfolioReservedStockEvent;
import com.tradeflow.portfolio.api.command.ReserveStockCommand;
import com.tradeflow.portfolio.api.command.UpdatePortfolioCommand;
import com.tradeflow.portfolio.api.command.PortfolioUpdatedEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PortfolioMapper {

    PortfolioUpdatedEvent portfolioToUpdatedEvent(UpdatePortfolioCommand portfolio);

    PortfolioReservedStockEvent reserveStockToPortfolioReserved(ReserveStockCommand reserveStockCommand);
}
