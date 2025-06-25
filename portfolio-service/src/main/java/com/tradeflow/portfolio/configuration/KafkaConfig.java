package com.tradeflow.portfolio.configuration;

import com.tradeflow.portfolio.api.command.PortfolioReservedStockEvent;
import com.tradeflow.portfolio.api.command.PortfolioUpdatedEvent;
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
    public ProducerFactory<UUID, PortfolioUpdatedEvent> portfolioUpdateProducerFactory(KafkaProperties props) {
        Map<String, Object> configs = props.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public KafkaTemplate<UUID, PortfolioUpdatedEvent> portfolioUpdateKafkaTemplate(
            ProducerFactory<UUID, PortfolioUpdatedEvent> portfolioUpdateProducerFactory) {
        return new KafkaTemplate<>(portfolioUpdateProducerFactory);
    }


    @Bean
    public ProducerFactory<UUID, PortfolioReservedStockEvent> portfoliReservedProducerFactory(KafkaProperties props) {
        Map<String, Object> configs = props.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public KafkaTemplate<UUID, PortfolioReservedStockEvent> portfoliReservedKafkaTemplate(
            ProducerFactory<UUID, PortfolioReservedStockEvent> portfoliReservedProducerFactory) {
        return new KafkaTemplate<>(portfoliReservedProducerFactory);
    }

}
