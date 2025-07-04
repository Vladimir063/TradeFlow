package com.tradeflow.company.repository;

import com.tradeflow.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
    List<Company> findByName(String name);

    List<Company> findCompanyByName(String name);
}
