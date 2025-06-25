package com.tradeflow.orders.dto;


import com.tradeflow.event.model.OrderType;
import com.tradeflow.event.model.Side;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreatedOrder(UUID userId,
                           UUID companyId,
                           Side side,
                           OrderType type,
                           Integer quantity,
                           Integer price) {
}