package com.tradeflow.orders.mapper;

import com.tradeflow.event.OrderCompletedEvent;
import com.tradeflow.event.OrderCreatedEvent;
import org.mapstruct.Mapper;
import com.tradeflow.orders.dto.CreatedOrder;
import com.tradeflow.event.OrderDto;
import com.tradeflow.orders.model.Order;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface OrderConverter {


    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "executionDate", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    Order dtoToEntity(CreatedOrder order);

    OrderCreatedEvent entityToEvent(Order order);

    OrderDto entityToDto(Order order);

    OrderCompletedEvent orderToOrderCompletedEvent(Order order);
}
