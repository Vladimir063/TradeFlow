package com.tradeflow.event.model;

public enum OrderType {
    MARKET, // Рыночная сделка (исполнить по лучшей доступной цене)
    LIMIT   // Лимитная сделка (исполнить по заданной цене или лучше)
}