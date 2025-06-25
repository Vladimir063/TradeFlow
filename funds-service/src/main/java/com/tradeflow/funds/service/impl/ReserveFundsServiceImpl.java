package com.tradeflow.funds.service.impl;

import com.tradeflow.company.api.entity.Company;
import com.tradeflow.event.OrderCompletedEvent;
import com.tradeflow.event.UserCreatedEvent;
import com.tradeflow.event.kafka.Topics;
import com.tradeflow.event.model.Side;
import com.tradeflow.funds.mapper.FundsConverter;
import com.tradeflow.funds.model.Account;
import com.tradeflow.funds.repository.jpa.AccountRepository;
import com.tradeflow.funds.service.ReserveFundsService;
import com.tradeflow.event.PaymentReservedEvent;
import com.tradeflow.event.model.OrderType;
import com.tradeflow.company.api.event.ReserveFundsCommand;
import com.tradeflow.funds.repository.redis.CompanyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;




@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReserveFundsServiceImpl implements ReserveFundsService {

    private final AccountRepository accountRepository;
    private final FundsConverter fundsConverter;
    private final KafkaTemplate<UUID, PaymentReservedEvent> kafkaTemplatePaymentReservedEvent;
    private final CompanyRepository companyRepository;

    @Override
    public void handleReserveFundsCommand(ReserveFundsCommand cmd) {
        Account account = findByUserId(cmd.userId());
        ReserveFundsCommand reserveFundsCommandWithPrice = fillPrice(cmd);
        Integer requiredAmount = calculateRequiredAmount(cmd, reserveFundsCommandWithPrice.price());
        tryReserve(account, requiredAmount, cmd);
    }

    private ReserveFundsCommand fillPrice(ReserveFundsCommand cmd) {
        if (cmd.type().equals(OrderType.LIMIT)) {
            return cmd ;
        } else {
            Company company = companyRepository.findById(cmd.companyId().toString())
                    .orElseThrow(() -> new RuntimeException("Company not found"));
            return new ReserveFundsCommand(cmd.orderId(), cmd.userId(), cmd.companyId(), cmd.side(), cmd.type(), cmd.quantity(), company.getAsk(), cmd.createdDate());
        }
    }

    @Override
    public void handleOrderCompletedEvent(OrderCompletedEvent orderCompletedEvent) {
        Account account = findByUserId(orderCompletedEvent.userId());
        if (orderCompletedEvent.side().equals(Side.BUY)) {
            account.setReserved(account.getReserved() - orderCompletedEvent.price() * orderCompletedEvent.quantity());
        }
        log.info("Account updated after orderCompletedEvent. Account =  {}", account );
    }

    @Override
    public void handleUserCreatedEvent(UserCreatedEvent userCreatedEvent) {
        Account account = fundsConverter.userCreatedEventToAccount(userCreatedEvent);
        account.setReserved(0);
        accountRepository.save(account);
        log.info("Account updated after userCreatedEvent. UserCreatedEvent =  {}", userCreatedEvent );
    }

    private void tryReserve(Account account, Integer requiredAmount, ReserveFundsCommand cmd) {
        PaymentReservedEvent paymentReservedEvent = fundsConverter.reserveFundsCommandToPaymentReserved(cmd);

        if (account.getAvailableMoney().compareTo(requiredAmount) < 0) {
            kafkaTemplatePaymentReservedEvent.send(Topics.PAYMENT_RESERVED_FAILED_EVENTS, cmd.orderId(), paymentReservedEvent);
            log.info("Send event to reserve failed {}", paymentReservedEvent);
            return;
        }
        account.setAvailableMoney(account.getAvailableMoney() - requiredAmount);
        account.setReserved(account.getReserved() + requiredAmount);
        accountRepository.save(account);
        kafkaTemplatePaymentReservedEvent.send(Topics.PAYMENT_RESERVED_EVENTS, cmd.orderId(), paymentReservedEvent);
        log.info("Send event to reserve successful {}", paymentReservedEvent);
    }

    private Integer calculateRequiredAmount(ReserveFundsCommand cmd, Integer price) {
        return cmd.quantity() * price;
    }

    private Account findByUserId(UUID userId) {
        return accountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No available user funds found"));
    }
}
