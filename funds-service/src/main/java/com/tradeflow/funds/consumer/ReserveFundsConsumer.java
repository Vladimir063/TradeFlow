package com.tradeflow.funds.consumer;


import com.tradeflow.funds.service.ReserveFundsService;
import com.tradeflow.event.UserCreatedEvent;
import com.tradeflow.event.kafka.Topics;
import com.tradeflow.company.api.event.ReserveFundsCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReserveFundsConsumer {

    private final ReserveFundsService reserveFundsService;

    @KafkaListener(topics = Topics.FUNDS_COMMANDS)
    public void onReserveFunds(ReserveFundsCommand reserveFundsCommand) {
        reserveFundsService.handleReserveFundsCommand(reserveFundsCommand);
    }

    @KafkaListener(topics = Topics.USER_CREATE_EVENTS)
    public void onUserCreated(UserCreatedEvent userCreatedEvent) {
        log.info("Received userCreated event: {}", userCreatedEvent);
        reserveFundsService.handleUserCreatedEvent(userCreatedEvent);
    }
}
