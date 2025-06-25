package com.tradeflow.orders.dto;



import com.tradeflow.event.model.Side;
import com.tradeflow.event.model.Status;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderDto(UUID orderId,
                       UUID userId,
                       Side side,
                       UUID companyId,
                       Integer quantity,
                       Integer price,
                       LocalDateTime createdDate,
                       Status status) {
}