package com.tradeflow.saga.consumer;

import com.tradeflow.saga.service.SagaService;
import com.tradeflow.event.PaymentReservedEvent;
import com.tradeflow.event.kafka.Topics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentReservedConsumer {

    private final SagaService sagaService;

    @KafkaListener(topics = Topics.PAYMENT_RESERVED_EVENTS)
    public void onPaymentReservedSuccess(PaymentReservedEvent event) {
        log.info("Received payment reserved event: {}", event);
        sagaService.handlePaymentReservedSuccess(event);
    }


}