package com.tradeflow.company.controller;

import com.tradeflow.company.api.event.CompanyCreatedEvent;
import com.tradeflow.company.api.event.CompanyDto;
import com.tradeflow.company.dto.CompanyCreateRequest;
import com.tradeflow.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/create")
    public CompanyCreatedEvent createCompany(@RequestBody CompanyCreateRequest companyCreateRequest) {
        return companyService.createCompany(companyCreateRequest);
    }

    @GetMapping("/companies")
    public List<CompanyDto> findAll() {
        return companyService.findAll();
    }

    @GetMapping("/{id}")
    public CompanyDto findById(@PathVariable UUID id) {
        return companyService.findById(id);
    }
}
