package com.tradeflow.funds.repository.redis;

import com.tradeflow.company.api.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
    List<Company> findByName(String name);

    List<Company> findCompanyByName(String name);
}
