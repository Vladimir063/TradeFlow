package com.tradeflow.saga.service;

import com.tradeflow.event.OrderCompletedEvent;
import com.tradeflow.event.OrderCreatedEvent;
import com.tradeflow.event.PaymentReservedEvent;
import com.tradeflow.portfolio.api.command.PortfolioReservedStockEvent;
import com.tradeflow.portfolio.api.command.PortfolioUpdatedEvent;
import com.tradeflow.trade.api.command.TradeExecutedEvent;

public interface SagaService {

    void handleOrderCreated(OrderCreatedEvent event);

    void handlePaymentReservedSuccess(PaymentReservedEvent event);

    void handleTradeExecuted(TradeExecutedEvent event);

    void handlePortfolioUpdate(PortfolioUpdatedEvent event);

    void handlePortfolioReserved(PortfolioReservedStockEvent event);
}
