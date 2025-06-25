package com.tradeflow.orders.configuration;

import com.tradeflow.event.CompleteOrderCommand;
import com.tradeflow.event.OrderCompletedEvent;
import com.tradeflow.event.OrderCreatedEvent;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;
import java.util.UUID;

@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<UUID, OrderCreatedEvent> orderCreateProducerFactory(KafkaProperties props) {
        Map<String, Object> configs = props.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public KafkaTemplate<UUID, OrderCreatedEvent> orderCreateKafkaTemplate(
            ProducerFactory<UUID, OrderCreatedEvent> orderCreateProducerFactory) {
        return new KafkaTemplate<>(orderCreateProducerFactory);
    }

    @Bean
    public ProducerFactory<UUID, OrderCompletedEvent> orderCompletedProducerFactory(KafkaProperties props) {
        Map<String, Object> configs = props.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public KafkaTemplate<UUID, OrderCompletedEvent> orderCompletedKafkaTemplate(
            ProducerFactory<UUID, OrderCompletedEvent> orderCompletedProducerFactory) {
        return new KafkaTemplate<>(orderCompletedProducerFactory);
    }
}