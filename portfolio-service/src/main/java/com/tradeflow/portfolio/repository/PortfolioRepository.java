package com.tradeflow.portfolio.repository;

import com.tradeflow.portfolio.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PortfolioRepository  extends JpaRepository<Portfolio, UUID> {

    Optional<Portfolio> findByUserIdAndCompanyId(UUID userId, UUID companyId);
}
