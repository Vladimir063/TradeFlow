package com.tradeflow.portfolio.api.command;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioDto {

    private UUID portfolioId;

    private UUID companyId;

    private UUID userId;

    private Integer available;

    private Integer reserved;
}
