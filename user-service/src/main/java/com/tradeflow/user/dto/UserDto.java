package com.tradeflow.user.dto;


import com.tradeflow.event.OrderDto;
import com.tradeflow.funds.api.AccountDto;
import com.tradeflow.portfolio.api.command.PortfolioDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {


     private UUID userId;
     private String username;
     private String firstName;
     private String lastName;
     private AccountDto accountDto;
     private PortfolioDto portfolioDto;
     private List<OrderDto> ordersDto;
}