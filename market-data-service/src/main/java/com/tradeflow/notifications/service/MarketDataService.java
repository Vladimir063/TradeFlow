package com.tradeflow.notifications.service;

import com.tradeflow.company.api.event.CompanyCreatedEvent;

public interface MarketDataService {


    void companyCreated(CompanyCreatedEvent event);
}
