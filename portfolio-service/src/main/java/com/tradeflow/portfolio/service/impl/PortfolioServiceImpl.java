package com.tradeflow.portfolio.service.impl;

import com.tradeflow.event.model.Side;
import com.tradeflow.portfolio.api.command.*;
import com.tradeflow.portfolio.mapper.PortfolioMapper;
import com.tradeflow.portfolio.model.Portfolio;
import com.tradeflow.portfolio.repository.PortfolioRepository;
import com.tradeflow.portfolio.service.PortfolioService;
import com.tradeflow.event.kafka.Topics;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final KafkaTemplate<UUID, PortfolioUpdatedEvent> kafkaTemplatePortfolioUpdate;
    private final KafkaTemplate<UUID, PortfolioReservedStockEvent> kafkaTemplateReservedEvent;
    private final PortfolioMapper portfolioMapper;

    @Override
    public void handleUpdatePortfolioCommand(UpdatePortfolioCommand updatePortfolioCommand) {
        Optional<Portfolio> portfolioMaybe = portfolioRepository.findByUserIdAndCompanyId(updatePortfolioCommand.userId(), updatePortfolioCommand.companyId());
        if (portfolioMaybe.isPresent()) {
            updateExistPortfolio(updatePortfolioCommand, portfolioMaybe.get());
        } else {
            createNewPortfolio(updatePortfolioCommand);
        }
    }

    @Override
    public void handleReserveStockCommand(ReserveStockCommand reserveStockCommand) {
        log.info("Начато резервирование акций по команде reserveStockCommand: {}", reserveStockCommand);
        PortfolioReservedStockEvent portfolioReservedStockEvent = portfolioMapper.reserveStockToPortfolioReserved(reserveStockCommand);
        if (reserveStockCommand.side().equals(Side.SELL)) {
            Portfolio portfolio = portfolioRepository.findByUserIdAndCompanyId(portfolioReservedStockEvent.userId(), portfolioReservedStockEvent.companyId())
                    .orElseThrow(() -> new RuntimeException("Portfolio not found"));
            if (portfolio.getAvailable().compareTo(portfolioReservedStockEvent.quantity()) < 0) {
                kafkaTemplateReservedEvent.send(Topics.PORTFOLIO_RESERVED_FAILED_EVENTS, portfolioReservedStockEvent.orderId(), portfolioReservedStockEvent);
                return;
            }
            portfolio.setAvailable(portfolio.getAvailable() - portfolioReservedStockEvent.quantity());
            portfolio.setReserved(portfolio.getReserved() + portfolioReservedStockEvent.quantity());
            kafkaTemplateReservedEvent.send(Topics.PORTFOLIO_RESERVED_EVENTS, portfolioReservedStockEvent.orderId(), portfolioReservedStockEvent);
        }
        log.info("Резервирование акций для продажи успешно завершено");
    }

    @Override
    public PortfolioDto getPortfolio(UUID userId) {
        Portfolio portfolio = portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
        return portfolioMapper.entityToDto(portfolio);
    }

    private void createNewPortfolio(UpdatePortfolioCommand updatePortfolioCommand) {
        Portfolio portfolio = new Portfolio(UUID.randomUUID(), updatePortfolioCommand.companyId(), updatePortfolioCommand.userId(), updatePortfolioCommand.quantity(), 0);
        portfolioRepository.save(portfolio);
        PortfolioUpdatedEvent portfolioUpdatedEvent = portfolioMapper.portfolioToUpdatedEvent(updatePortfolioCommand);
        kafkaTemplatePortfolioUpdate.send(Topics.PORTFOLIO_EXECUTED, updatePortfolioCommand.orderId(), portfolioUpdatedEvent);
        log.info("Создание нового счета с акциями успешно завершено. Portfolio: {}", portfolio);
    }

    private void updateExistPortfolio(UpdatePortfolioCommand updatePortfolioCommand, Portfolio portfolio) {
        PortfolioUpdatedEvent portfolioUpdatedEvent = portfolioMapper.portfolioToUpdatedEvent(updatePortfolioCommand);
        if (updatePortfolioCommand.side().equals(Side.SELL)) {
            portfolio.setReserved(portfolio.getReserved() - updatePortfolioCommand.quantity());
            kafkaTemplatePortfolioUpdate.send(Topics.PORTFOLIO_EXECUTED, updatePortfolioCommand.orderId(), portfolioUpdatedEvent);
        } else {
            portfolio.setAvailable(portfolio.getAvailable() + updatePortfolioCommand.quantity());
            portfolioRepository.save(portfolio);
            kafkaTemplatePortfolioUpdate.send(Topics.PORTFOLIO_EXECUTED, updatePortfolioCommand.orderId(), portfolioUpdatedEvent);
        }
        log.info("Обновление существующего счета с акциями успешно завершено. Portfolio: {}", portfolio);
    }
}
