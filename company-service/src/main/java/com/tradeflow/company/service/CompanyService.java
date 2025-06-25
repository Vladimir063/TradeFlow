package com.tradeflow.company.service;

import com.tradeflow.company.api.event.CompanyCreatedEvent;
import com.tradeflow.company.api.event.CompanyDto;
import com.tradeflow.company.dto.CompanyCreateRequest;

import java.util.List;
import java.util.UUID;

public interface CompanyService {

    CompanyCreatedEvent createCompany(CompanyCreateRequest companyCreateRequest);

    List<CompanyDto> findAll();

    CompanyDto findById(UUID id);
}
