package com.tradeflow.company.api.event;

import java.util.UUID;

public record CompanyDto(UUID companyId, String name, Integer ask, Integer bid) {
}
