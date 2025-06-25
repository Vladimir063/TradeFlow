package com.tradeflow.company.mapper;

import com.tradeflow.company.api.event.CompanyCreatedEvent;
import com.tradeflow.company.api.event.CompanyDto;
import com.tradeflow.company.dto.CompanyCreateRequest;
import com.tradeflow.company.entity.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    Company dtoToEntity(CompanyCreateRequest companyCreateRequest);

    CompanyCreatedEvent entityToEvent(Company companySaved);

    CompanyDto entityToDto(Company companySaved);

}
