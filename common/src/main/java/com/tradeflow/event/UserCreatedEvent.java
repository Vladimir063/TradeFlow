package com.tradeflow.event;

import java.util.UUID;

public record UserCreatedEvent(
        UUID userId,
        String firstName,
        String lastName,
        Integer availableMoney
) {}