package com.tradeflow.funds.configuration;

import com.tradeflow.event.PaymentReservedEvent;
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
    public ProducerFactory<UUID, PaymentReservedEvent> reserveFundsProducerFactory(KafkaProperties props) {
        Map<String, Object> configs = props.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public KafkaTemplate<UUID, PaymentReservedEvent> reserveFundsKafkaTemplate(
            ProducerFactory<UUID, PaymentReservedEvent> reserveFundsProducerFactory) {
        return new KafkaTemplate<>(reserveFundsProducerFactory);
    }
}
