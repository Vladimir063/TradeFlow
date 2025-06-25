package com.tradeflow.execution.consumer;

import com.tradeflow.execution.services.ExecuteTradeService;
import com.tradeflow.event.kafka.Topics;
import com.tradeflow.trade.api.command.ExecuteTradeCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExecuteTradeCommandConsumer {

    private final ExecuteTradeService executeTradeService;

    @KafkaListener(topics = Topics.TRADE_COMMANDS)
    public void onOrderCreated(ExecuteTradeCommand executeTradeCommand) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.warn("Received executeTradeCommand: {}", executeTradeCommand);
        executeTradeService.handleExecuteTradeCommand(executeTradeCommand);
    }
}
