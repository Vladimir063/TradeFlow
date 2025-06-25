package com.tradeflow.user.dto;


import java.util.UUID;

public record UserDto(
        UUID userId,
        String firstName,
        String lastName,
        Integer availableMoney
) {}