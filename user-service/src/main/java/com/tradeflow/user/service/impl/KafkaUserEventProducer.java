package com.tradeflow.user.service.impl;

import com.tradeflow.user.service.UserEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.tradeflow.event.kafka.Topics;
import com.tradeflow.event.UserCreatedEvent;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaUserEventProducer implements UserEventProducer {


    private final KafkaTemplate<UUID, UserCreatedEvent> kafkaTemplateUserCreated;


    @Override
    public void publishUserCreatedEvent(UserCreatedEvent userCreatedEvent) {
        kafkaTemplateUserCreated.send(Topics.USER_CREATE_EVENTS, userCreatedEvent.userId(), userCreatedEvent);
    }
}
