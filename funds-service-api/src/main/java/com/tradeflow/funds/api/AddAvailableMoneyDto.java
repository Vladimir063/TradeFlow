package com.tradeflow.funds.api;

import java.io.Serializable;
import java.util.UUID;

public record AddAvailableMoneyDto(UUID userId, int amount) {
}
