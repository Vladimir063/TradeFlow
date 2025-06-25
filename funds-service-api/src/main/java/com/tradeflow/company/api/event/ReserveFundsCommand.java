package com.tradeflow.company.api.event;

import com.tradeflow.event.model.OrderType;
import com.tradeflow.event.model.Side;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReserveFundsCommand(UUID orderId,
                                  UUID userId,
                                  UUID companyId,
                                  Side side,
                                  OrderType type,
                                  Integer quantity,
                                  Integer price,
                                  LocalDateTime createdDate) {
}