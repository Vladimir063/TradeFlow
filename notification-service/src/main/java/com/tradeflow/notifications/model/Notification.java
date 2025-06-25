package com.tradeflow.notifications.model;

import com.tradeflow.event.model.OrderType;
import com.tradeflow.event.model.Side;
import com.tradeflow.event.model.Status;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "notification")
@Data
public class Notification {

    @Id
    private UUID notificationId;

    private NotificationType notificationType;

    private UUID orderId;

    private Side side;

    private Integer quantity;

    private Integer price;

    private LocalDateTime createdDate;

    private LocalDateTime executionDate;

    private UUID companyId;

    private UUID userId;

    private Status status;

    private OrderType type;

}
