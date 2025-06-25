package com.tradeflow.company.api.event;

import java.util.UUID;

public record CompanyCreatedEvent(UUID companyId, String name, Integer ask, Integer bid) {
}
