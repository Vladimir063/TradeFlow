package com.tradeflow.funds.api;

import java.util.UUID;

public record AccountDto( UUID userId, Integer availableMoney, Integer reserved){
}
