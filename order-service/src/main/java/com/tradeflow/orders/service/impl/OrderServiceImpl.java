package com.tradeflow.orders.service.impl;

import com.tradeflow.event.CompleteOrderCommand;
import com.tradeflow.event.OrderCompletedEvent;
import com.tradeflow.event.OrderCreatedEvent;
import com.tradeflow.event.model.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import com.tradeflow.orders.dto.CreatedOrder;
import com.tradeflow.orders.dto.OrderDto;
import com.tradeflow.orders.mapper.OrderConverter;
import com.tradeflow.orders.model.Order;
import com.tradeflow.orders.repository.OrderRepository;
import com.tradeflow.orders.service.OrderEventProducer;
import com.tradeflow.orders.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final OrderEventProducer orderEventProducer;

    @Override
    public OrderCreatedEvent orderCreated(CreatedOrder createdOrder) {
        Order order = orderConverter.dtoToEntity(createdOrder);
        order.setCreatedDate(LocalDateTime.now());
        Order orderSave = orderRepository.save(order);
        OrderCreatedEvent orderCreatedEvent = orderConverter.entityToEvent(orderSave);
        orderEventProducer.publishOrderCreatedEvent(orderCreatedEvent);
        log.info("Order created event: {}", orderCreatedEvent);
        return orderCreatedEvent;
    }

    @Override
    public List<OrderDto> findByUserId(UUID userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return  orders.stream()
                .map(orderConverter::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderDtoById(UUID orderId) {
        Order orderMaybe = findByOrderId(orderId);
        return orderConverter.entityToDto(orderMaybe);
    }

    private Order findByOrderId(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public void orderCompleted(CompleteOrderCommand completeOrderCommand) {
        Order order = findByOrderId(completeOrderCommand.orderId());
        order.setExecutionDate(LocalDateTime.now());
        order.setStatus(Status.DONE);
        OrderCompletedEvent orderCompletedEvent = orderConverter.orderToOrderCompletedEvent(order);
        orderEventProducer.publishOrderCompletedEvent(orderCompletedEvent);
        log.info("Order completed event: {}", orderCompletedEvent);
    }
}
