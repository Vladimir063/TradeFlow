package com.tradeflow.funds.service;

import com.tradeflow.event.OrderCompletedEvent;
import com.tradeflow.company.api.event.ReserveFundsCommand;
import com.tradeflow.event.UserCreatedEvent;

public interface ReserveFundsService {


    void handleReserveFundsCommand(ReserveFundsCommand reserveFundsCommand);

    void handleOrderCompletedEvent(OrderCompletedEvent orderCompletedEvent);

    void handleUserCreatedEvent(UserCreatedEvent userCreatedEvent);
}
