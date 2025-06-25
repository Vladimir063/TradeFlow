package com.tradeflow.company.service;

import com.tradeflow.company.api.event.CompanyCreatedEvent;

public interface CompanyEventProducer {

    void publishCompanyCreated(CompanyCreatedEvent event);
}
