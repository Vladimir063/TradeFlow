package com.tradeflow.portfolio.controller;

import com.tradeflow.portfolio.api.command.PortfolioDto;
import com.tradeflow.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/portfolio/{userId}")
    public PortfolioDto getPortfolio(@PathVariable UUID userId) {
        return portfolioService.getPortfolio(userId);
    }

}
