package com.tradeflow.notifications.service.impl;

import com.tradeflow.event.OrderCompletedEvent;
import com.tradeflow.notifications.mapper.NotificationMapper;
import com.tradeflow.notifications.model.Notification;
import com.tradeflow.notifications.model.NotificationType;
import com.tradeflow.notifications.repository.NotificationRepository;
import com.tradeflow.notifications.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;

    @Override
    public void handleOrderCompleted(OrderCompletedEvent orderCompletedEvent) {
        Notification notification = notificationMapper.orderCompletedToNotification(orderCompletedEvent);
        sentWebSocket(notification);
        notificationRepository.save(notification);
    }

    private void sentWebSocket(Notification notification) {
        notification.setNotificationId(UUID.randomUUID());
        notification.setNotificationType(NotificationType.WEB_SOCKET);
    }
}
