package com.tradeflow.notifications.service.impl;

import com.tradeflow.company.api.event.CompanyCreatedEvent;
import com.tradeflow.notifications.model.PriceQuote;
import com.tradeflow.notifications.repository.PriceQuoteRepository;
import com.tradeflow.notifications.service.MarketDataService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

// Эмитация подключения к внешнему API для получение цены акций
@Service
@RequiredArgsConstructor
public class MarketDataServiceImpl implements MarketDataService {


    private final PriceQuoteRepository priceQuoteRepository;
    private final Map<UUID, PriceQuote> prices = new HashMap<>();

    private final ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor(r -> {
                Thread t = new Thread(r, "generate-prices");
                t.setDaemon(true);
                return t;
            });

    @PostConstruct
    public void init(){
        scheduler.scheduleAtFixedRate(this::generatePrices, 0, 10, TimeUnit.SECONDS);
    }

    private void generatePrices(){
        prices.replaceAll((companyId, oldPrice) -> {
            int delta = ThreadLocalRandom.current().nextInt(1, 4);
            if (ThreadLocalRandom.current().nextBoolean()) {
                delta = -delta;
            }
            oldPrice.setAsk(oldPrice.getAsk() + delta);
            oldPrice.setBid(oldPrice.getBid() + delta);
            return oldPrice;
        });
        prices.forEach((companyId, price) -> {
            priceQuoteRepository.save(price);
        });
    }

    @Override
    public void companyCreated(CompanyCreatedEvent event) {
        PriceQuote priceQuote = new PriceQuote(event.companyId(), event.name(), event.bid(), event.ask());
        prices.put(priceQuote.getCompanyId(), priceQuote);
    }
}
