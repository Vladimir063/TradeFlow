package com.tradeflow.saga.mapper;

import com.tradeflow.event.CompleteOrderCommand;
import com.tradeflow.event.OrderCompletedEvent;
import com.tradeflow.portfolio.api.command.PortfolioReservedStockEvent;
import com.tradeflow.portfolio.api.command.ReserveStockCommand;
import com.tradeflow.portfolio.api.command.UpdatePortfolioCommand;
import com.tradeflow.saga.dto.SagaCompeted;
import com.tradeflow.event.OrderCreatedEvent;
import com.tradeflow.event.PaymentReservedEvent;
import com.tradeflow.company.api.event.ReserveFundsCommand;
import com.tradeflow.portfolio.api.command.PortfolioUpdatedEvent;
import com.tradeflow.trade.api.command.ExecuteTradeCommand;
import com.tradeflow.trade.api.command.TradeExecutedEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SagaMapper {


    ReserveFundsCommand orderCreatedToReserveFunds(OrderCreatedEvent orderCreatedEvent);

    ExecuteTradeCommand paymentReservedToExecuteTrade(PaymentReservedEvent event);

    UpdatePortfolioCommand tradeExecutedToUpdatePortfolioCommand(TradeExecutedEvent tradeExecutedEvent);

    SagaCompeted portfolioUpdatedEventToSagaCompeted(OrderCompletedEvent event);

    CompleteOrderCommand portfolioUpdatedEventToCompleteOrderCommand(PortfolioUpdatedEvent event);

    ReserveStockCommand orderCreatedToReserveStock(OrderCreatedEvent event);

    ExecuteTradeCommand portfolioReservedToExecuteTrade(PortfolioReservedStockEvent event);
}
