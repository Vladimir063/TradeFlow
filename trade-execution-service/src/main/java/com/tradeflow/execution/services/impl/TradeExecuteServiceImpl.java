package com.tradeflow.execution.services.impl;

import com.tradeflow.company.api.entity.Company;
import com.tradeflow.event.model.Side;
import com.tradeflow.event.model.Status;
import com.tradeflow.execution.mapper.TradeExecutionMapper;
import com.tradeflow.execution.model.OrderProjection;
import com.tradeflow.execution.repository.redis.CompanyRepository;
import com.tradeflow.execution.repository.jpa.OrderProjectionRepository;
import com.tradeflow.execution.services.ExecuteTradeService;
import com.tradeflow.event.OrderCreatedEvent;
import com.tradeflow.event.kafka.Topics;
import com.tradeflow.event.model.OrderType;
import com.tradeflow.trade.api.command.ExecuteTradeCommand;
import com.tradeflow.trade.api.command.TradeExecutedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TradeExecuteServiceImpl implements ExecuteTradeService {

    private final OrderProjectionRepository orderProjectionRepository;
    private final KafkaTemplate<UUID, TradeExecutedEvent> kafkaTemplate;
    private final TradeExecutionMapper tradeExecutionMapper;
    private final CompanyRepository companyRepository;


    @Override
    public void handleExecuteTradeCommand(ExecuteTradeCommand executeTradeCommand) {
        int sharePrice = getSharePrice(executeTradeCommand);
        OrderProjection orderFromCommand = getOrderProjection(executeTradeCommand);
        Optional<OrderProjection> oppositeOrderMaybe
                = orderProjectionRepository.findOppositeOrder(orderFromCommand.getSide(), sharePrice, orderFromCommand.getQuantity(), orderFromCommand.getCompanyId(), orderFromCommand.getUserId());
        if (oppositeOrderMaybe.isPresent()) {
            orderFromCommand.setStatus(Status.DONE);
            OrderProjection orderOpposite = oppositeOrderMaybe.get();
            orderOpposite.setStatus(Status.DONE);
            TradeExecutedEvent tradeExecutedEventFromCommand = tradeExecutionMapper.orderToTradeExecuted(orderFromCommand);
            TradeExecutedEvent tradeExecutedEventOpposite = tradeExecutionMapper.orderToTradeExecuted(orderOpposite);
            kafkaTemplate.send(Topics.TRADE_EXECUTED, tradeExecutedEventFromCommand.orderId(), tradeExecutedEventFromCommand);
            kafkaTemplate.send(Topics.TRADE_EXECUTED, tradeExecutedEventOpposite.orderId(), tradeExecutedEventOpposite);
            log.info("Send tradeExecutedEventFromCommand: {}", tradeExecutedEventFromCommand);
            log.info("Send tradeExecutedEventOpposite: {}", tradeExecutedEventOpposite);
        }
    }

    @Override
    public void handleOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        OrderProjection orderProjection = tradeExecutionMapper.orderCreatedEventToOrderProjection(orderCreatedEvent);
        orderProjectionRepository.save(orderProjection);
        log.info("orderProjection saved: {}", orderProjection);
    }

    private int getSharePrice(ExecuteTradeCommand executeTradeCommand) {
        if (executeTradeCommand.type().equals(OrderType.LIMIT)) {
            return executeTradeCommand.price();
        } else if (executeTradeCommand.type().equals(OrderType.MARKET) && executeTradeCommand.side().equals(Side.SELL)) {
            return executeTradeCommand.price();
        } else {
            Company company = companyRepository.findById(executeTradeCommand.companyId().toString())
                    .orElseThrow(() -> new RuntimeException("Company not found"));
            return company.getBid();
        }
    }

    private OrderProjection getOrderProjection(ExecuteTradeCommand executeTradeCommand) {
        return orderProjectionRepository.findById(executeTradeCommand.orderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
