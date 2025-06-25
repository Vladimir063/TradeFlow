package com.tradeflow.execution.services;

import com.tradeflow.event.OrderCreatedEvent;
import com.tradeflow.trade.api.command.ExecuteTradeCommand;

public interface ExecuteTradeService {

    void handleExecuteTradeCommand(ExecuteTradeCommand executeTradeCommand);

    void handleOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent);
}
