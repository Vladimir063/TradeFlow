package com.tradeflow.portfolio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Portfolio {

    @Id
    private UUID portfolioId;

    private UUID companyId;

    private UUID userId;

    private Integer available;

    private Integer reserved;
}
