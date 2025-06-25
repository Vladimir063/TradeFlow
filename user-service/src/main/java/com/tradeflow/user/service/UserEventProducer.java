package com.tradeflow.user.service;
import com.tradeflow.event.UserCreatedEvent;


public interface UserEventProducer {

    void publishUserCreatedEvent(UserCreatedEvent userCreatedEvent);
}
