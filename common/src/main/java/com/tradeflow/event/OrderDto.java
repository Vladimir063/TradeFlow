package com.tradeflow.event;



import com.tradeflow.event.model.Side;
import com.tradeflow.event.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private UUID orderId;
    private UUID userId;
    private Side side;
    private String companyName;
    private Integer quantity;
    private Integer price;
    private LocalDateTime createdDate;
    private Status status;

}