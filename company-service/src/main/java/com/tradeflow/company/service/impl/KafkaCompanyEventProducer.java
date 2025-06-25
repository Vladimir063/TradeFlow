package com.tradeflow.company.service.impl;

import com.tradeflow.company.api.event.CompanyCreatedEvent;
import com.tradeflow.company.service.CompanyEventProducer;
import com.tradeflow.event.kafka.Topics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaCompanyEventProducer implements CompanyEventProducer {

    private final KafkaTemplate<UUID, CompanyCreatedEvent> kafkaTemplateCompanyCreated;


    @Override
    public void publishCompanyCreated(CompanyCreatedEvent event) {
        kafkaTemplateCompanyCreated.send(Topics.COMPANY_CREATED_EVENTS, event.companyId(), event);
    }
}
