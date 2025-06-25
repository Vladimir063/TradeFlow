package com.tradeflow.execution.model;

import com.tradeflow.event.model.OrderType;
import com.tradeflow.event.model.Side;
import com.tradeflow.event.model.Status;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "order_projection")
public class OrderProjection {

    @Id
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    private Side side;

    private Integer quantity;

    private Integer price;

    private LocalDateTime createdDate;

    private UUID companyId;

    private UUID userId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private OrderType type;
}
