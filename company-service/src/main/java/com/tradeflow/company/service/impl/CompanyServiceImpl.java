package com.tradeflow.company.service.impl;

import com.tradeflow.company.api.event.CompanyCreatedEvent;
import com.tradeflow.company.api.event.CompanyDto;
import com.tradeflow.company.dto.CompanyCreateRequest;
import com.tradeflow.company.entity.Company;
import com.tradeflow.company.mapper.CompanyMapper;
import com.tradeflow.company.repository.CompanyRepository;
import com.tradeflow.company.service.CompanyEventProducer;
import com.tradeflow.company.service.CompanyService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final CompanyEventProducer companyEventProducer;

    private final ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor(r -> {
                Thread t = new Thread(r, "generate-prices");
                t.setDaemon(true);
                return t;
            });

    @PostConstruct
    public void init() {
        scheduler.scheduleAtFixedRate(this::generatePrices, 0, 10, TimeUnit.SECONDS);
    }

    @Override
    public CompanyCreatedEvent createCompany(CompanyCreateRequest companyCreateRequest) {
        List<Company> companies = companyRepository.findByName(companyCreateRequest.name());
        if (companies.isEmpty()) {
            Company company = companyMapper.dtoToEntity(companyCreateRequest);
            company.setCompanyId(UUID.randomUUID().toString());
            Company companySaved = companyRepository.save(company);
            CompanyCreatedEvent companyCreatedEvent = companyMapper.entityToEvent(companySaved);
            companyEventProducer.publishCompanyCreated(companyCreatedEvent);
            log.info("Created new company: {}", companyCreatedEvent);
            return companyCreatedEvent;
        } else {
            throw new RuntimeException("Company already exists");
        }
    }

    @Override
    public List<CompanyDto> findAll() {
        return companyRepository.findAll().stream()
                .map(companyMapper::entityToDto)
                .toList();
    }

    @Override
    public CompanyDto findById(UUID id) {
        Company company = companyRepository.findById(id.toString())
                .orElseThrow(() -> new RuntimeException("Company not found"));
        return companyMapper.entityToDto(company);
    }


    private void generatePrices() {
        Iterable<Company> companies = companyRepository.findAll();
        companies.forEach(company -> {
                    int delta = ThreadLocalRandom.current().nextInt(1, 4);
                    if (ThreadLocalRandom.current().nextBoolean()) {
                        delta = -delta;
                    }
                    company.setAsk(company.getAsk() + delta);
                    company.setBid(company.getBid() + delta);
                    companyRepository.save(company);
                });
    }
}
