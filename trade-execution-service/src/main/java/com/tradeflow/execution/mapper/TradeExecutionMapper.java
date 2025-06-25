package com.tradeflow.execution.mapper;

import com.tradeflow.execution.model.OrderProjection;
import com.tradeflow.event.OrderCreatedEvent;
import com.tradeflow.trade.api.command.TradeExecutedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TradeExecutionMapper {

    TradeExecutedEvent orderCommandToTradeExecuted(OrderProjection order);

    @Mapping(target = "status", constant = "PENDING")
    OrderProjection orderCreatedEventToOrderProjection(OrderCreatedEvent orderCreatedEvent);

    TradeExecutedEvent orderToTradeExecuted(OrderProjection orderFromCommand);
}
