package com.tradeflow.user.dto;

public record UserCreateRequest(String firstName,
                                String lastName,
                                Integer availableMoney) {
}
