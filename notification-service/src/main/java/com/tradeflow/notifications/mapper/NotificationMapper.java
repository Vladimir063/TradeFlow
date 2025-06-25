package com.tradeflow.notifications.mapper;

import com.tradeflow.event.OrderCompletedEvent;
import com.tradeflow.notifications.model.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    Notification orderCompletedToNotification(OrderCompletedEvent orderCompletedEvent);
}
