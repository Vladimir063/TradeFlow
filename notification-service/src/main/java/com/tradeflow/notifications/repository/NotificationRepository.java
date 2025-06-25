package com.tradeflow.notifications.repository;

import com.tradeflow.notifications.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface NotificationRepository extends MongoRepository<Notification, UUID> {
}
