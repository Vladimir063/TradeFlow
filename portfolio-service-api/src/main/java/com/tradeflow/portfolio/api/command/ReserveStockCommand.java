package com.tradeflow.portfolio.api.command;

import com.tradeflow.event.model.OrderType;
import com.tradeflow.event.model.Side;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReserveStockCommand(UUID orderId,
                                  UUID userId,
                                  UUID companyId,
                                  Side side,
                                  OrderType type,
                                  Integer quantity,
                                  Integer price,
                                  LocalDateTime createdDate) {
}