package com.tradeflow.saga.service.iml;

import com.tradeflow.event.CompleteOrderCommand;
import com.tradeflow.event.model.Side;
import com.tradeflow.portfolio.api.command.PortfolioReservedStockEvent;
import com.tradeflow.portfolio.api.command.ReserveStockCommand;
import com.tradeflow.saga.mapper.SagaMapper;
import com.tradeflow.saga.service.SagaService;
import com.tradeflow.event.OrderCreatedEvent;
import com.tradeflow.event.PaymentReservedEvent;
import com.tradeflow.event.kafka.Topics;
import com.tradeflow.company.api.event.ReserveFundsCommand;
import com.tradeflow.portfolio.api.command.PortfolioUpdatedEvent;
import com.tradeflow.portfolio.api.command.UpdatePortfolioCommand;
import com.tradeflow.trade.api.command.ExecuteTradeCommand;
import com.tradeflow.trade.api.command.TradeExecutedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SagaServiceImpl implements SagaService {

    private final KafkaTemplate<UUID, ReserveFundsCommand> kafkaTemplateReserveFunds;
    private final KafkaTemplate<UUID, ExecuteTradeCommand> kafkaTemplateExecuteTrade;
    private final KafkaTemplate<UUID, UpdatePortfolioCommand> kafkaTemplatePortfolioUpdated;
    private final KafkaTemplate<UUID, CompleteOrderCommand> kafkaTemplateCompleteOrder;
    private final KafkaTemplate<UUID, ReserveStockCommand> kafkaTemplateReserveStock;
    private final SagaMapper sagaMapper;

    @Override
    public void handleOrderCreated(OrderCreatedEvent event) {
        if (event.side().equals(Side.BUY)) {
            ReserveFundsCommand reserveFundsCommand = sagaMapper.orderCreatedToReserveFunds(event);
            kafkaTemplateReserveFunds.send(Topics.FUNDS_COMMANDS, reserveFundsCommand.orderId(), reserveFundsCommand);
            log.info("Send reserveFundsCommand {}", reserveFundsCommand);
        } else if (event.side().equals(Side.SELL)) {
            ReserveStockCommand reserveStockCommand = sagaMapper.orderCreatedToReserveStock(event);
            kafkaTemplateReserveStock.send(Topics.PORTFOLIO_COMMANDS_RESERVE, reserveStockCommand.orderId(), reserveStockCommand);
        }
    }

    @Override
    public void handlePaymentReservedSuccess(PaymentReservedEvent event) {
        ExecuteTradeCommand executeTradeCommand = sagaMapper.paymentReservedToExecuteTrade(event);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        kafkaTemplateExecuteTrade.send(Topics.TRADE_COMMANDS, event.orderId(), executeTradeCommand);
        log.info("Send executeTradeCommand {}", executeTradeCommand);
    }

    @Override
    public void handlePortfolioReserved(PortfolioReservedStockEvent event) {
        ExecuteTradeCommand executeTradeCommand = sagaMapper.portfolioReservedToExecuteTrade(event);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        kafkaTemplateExecuteTrade.send(Topics.TRADE_COMMANDS, event.orderId(), executeTradeCommand);
        log.info("Send executeTradeCommand {}", executeTradeCommand);

    }

    @Override
    public void handleTradeExecuted(TradeExecutedEvent event) {
        UpdatePortfolioCommand updatePortfolioCommand = sagaMapper.tradeExecutedToUpdatePortfolioCommand(event);
        kafkaTemplatePortfolioUpdated.send(Topics.PORTFOLIO_COMMANDS_UPDATE, updatePortfolioCommand.orderId(), updatePortfolioCommand);
        log.info("Send updatePortfolioCommand {}", updatePortfolioCommand);
    }

    @Override
    public void handlePortfolioUpdate(PortfolioUpdatedEvent event) {
        CompleteOrderCommand completeOrderCommand = sagaMapper.portfolioUpdatedEventToCompleteOrderCommand(event);
        kafkaTemplateCompleteOrder.send(Topics.ORDER_COMMANDS, event.orderId(), completeOrderCommand);
        log.info("Send completeOrderCommand {}", completeOrderCommand);
    }
}
