package com.tradeflow.notifications.consumer;

import com.tradeflow.company.api.event.CompanyCreatedEvent;
import com.tradeflow.event.kafka.Topics;
import com.tradeflow.notifications.service.MarketDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyCreatedConsumer {

    private final MarketDataService marketDataService;

    @KafkaListener(topics = Topics.COMPANY_CREATED_EVENTS)
    public void onCompanyCreated(CompanyCreatedEvent event) {
        marketDataService.companyCreated(event);
    }
}
