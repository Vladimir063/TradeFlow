package com.tradeflow.portfolio.mapper;

import com.tradeflow.portfolio.api.command.*;
import com.tradeflow.portfolio.model.Portfolio;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PortfolioMapper {

    PortfolioUpdatedEvent portfolioToUpdatedEvent(UpdatePortfolioCommand portfolio);

    PortfolioReservedStockEvent reserveStockToPortfolioReserved(ReserveStockCommand reserveStockCommand);

    PortfolioDto entityToDto(Portfolio portfolio);
}
