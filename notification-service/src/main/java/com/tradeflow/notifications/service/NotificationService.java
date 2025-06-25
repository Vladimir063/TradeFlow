package com.tradeflow.notifications.service;

import com.tradeflow.event.OrderCompletedEvent;

public interface NotificationService {


    void handleOrderCompleted(OrderCompletedEvent orderCompletedEvent);
}
