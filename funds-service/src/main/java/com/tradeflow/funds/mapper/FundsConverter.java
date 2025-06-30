package com.tradeflow.funds.mapper;

import com.tradeflow.event.PaymentReservedEvent;
import com.tradeflow.company.api.event.ReserveFundsCommand;
import com.tradeflow.event.UserCreatedEvent;
import com.tradeflow.funds.api.AccountDto;
import com.tradeflow.funds.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FundsConverter {

    PaymentReservedEvent reserveFundsCommandToPaymentReserved(ReserveFundsCommand command);

    Account userCreatedEventToAccount(UserCreatedEvent event);

    AccountDto entityToDto(Account account);
}
