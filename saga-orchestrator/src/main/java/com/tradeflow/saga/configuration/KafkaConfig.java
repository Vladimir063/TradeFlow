package com.tradeflow.saga.configuration;

import com.tradeflow.event.CompleteOrderCommand;
import com.tradeflow.company.api.event.ReserveFundsCommand;
import com.tradeflow.portfolio.api.command.ReserveStockCommand;
import com.tradeflow.portfolio.api.command.UpdatePortfolioCommand;
import com.tradeflow.saga.dto.SagaCompeted;
import com.tradeflow.trade.api.command.ExecuteTradeCommand;
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
    public ProducerFactory<UUID, ReserveFundsCommand> reserveFundsProducerFactory(KafkaProperties props) {
        Map<String, Object> configs = props.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public KafkaTemplate<UUID, ReserveFundsCommand> reserveFundsKafkaTemplate(
            ProducerFactory<UUID, ReserveFundsCommand> reserveFundsProducerFactory) {
        return new KafkaTemplate<>(reserveFundsProducerFactory);
    }

    @Bean
    public ProducerFactory<UUID, ExecuteTradeCommand> executeTradeProducerFactory(KafkaProperties props) {
        Map<String, Object> configs = props.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public KafkaTemplate<UUID, ExecuteTradeCommand> executeTradeKafkaTemplate(
            ProducerFactory<UUID, ExecuteTradeCommand> executeTradeProducerFactory) {
        return new KafkaTemplate<>(executeTradeProducerFactory);
    }

    @Bean
    public ProducerFactory<UUID, UpdatePortfolioCommand> portfolioUpdateProducerFactory(KafkaProperties props) {
        Map<String, Object> configs = props.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public KafkaTemplate<UUID, UpdatePortfolioCommand> portfolioUpdateKafkaTemplate(
            ProducerFactory<UUID, UpdatePortfolioCommand> portfolioUpdateProducerFactory) {
        return new KafkaTemplate<>(portfolioUpdateProducerFactory);
    }

    @Bean
    public ProducerFactory<UUID, SagaCompeted> sagaCompletedProducerFactory(KafkaProperties props) {
        Map<String, Object> configs = props.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public KafkaTemplate<UUID, SagaCompeted> sagaCompletedKafkaTemplate(
            ProducerFactory<UUID, SagaCompeted> sagaCompletedProducerFactory) {
        return new KafkaTemplate<>(sagaCompletedProducerFactory);
    }


    @Bean
    public ProducerFactory<UUID, CompleteOrderCommand> completeOrderProducerFactory(KafkaProperties props) {
        Map<String, Object> configs = props.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public KafkaTemplate<UUID, CompleteOrderCommand> completeOrderdKafkaTemplate(
            ProducerFactory<UUID, CompleteOrderCommand> completeOrderProducerFactory) {
        return new KafkaTemplate<>(completeOrderProducerFactory);
    }

    @Bean
    public ProducerFactory<UUID, ReserveStockCommand> reserveStockProducerFactory(KafkaProperties props) {
        Map<String, Object> configs = props.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public KafkaTemplate<UUID, ReserveStockCommand> reserveStockKafkaTemplate(
            ProducerFactory<UUID, ReserveStockCommand> reserveStockProducerFactory) {
        return new KafkaTemplate<>(reserveStockProducerFactory);
    }

}