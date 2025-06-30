package com.tradeflow.funds.controller;

import com.tradeflow.funds.api.AccountDto;
import com.tradeflow.funds.api.AddAvailableMoneyDto;
import com.tradeflow.funds.service.ReserveFundsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {

    private final ReserveFundsService reserveFundsService;

    @PostMapping("/addAvailableMoney")
    public void addAvailableMoney(@RequestBody AddAvailableMoneyDto addAvailableMoneyDto) {
        reserveFundsService.addAvailableMoney(addAvailableMoneyDto.userId(), addAvailableMoneyDto.amount());
    }

    @GetMapping("/account/{userId}")
    public AccountDto getAvailableMoney(@PathVariable UUID userId) {
        AccountDto accountDto = reserveFundsService.getAvailableMoney(userId);
        System.out.printf("");
        return accountDto;
    }
}
