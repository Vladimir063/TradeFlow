package com.tradeflow.trade.api.command;

import com.tradeflow.event.model.OrderType;
import com.tradeflow.event.model.Side;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TradeExecutedEvent(UUID orderId,
                                 UUID userId,
                                 UUID companyId,
                                 Side side,
                                 OrderType type,
                                 Integer quantity,
                                 Integer price,
                                 LocalDateTime createdDate) {
}