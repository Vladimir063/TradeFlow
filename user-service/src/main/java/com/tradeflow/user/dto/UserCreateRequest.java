package com.tradeflow.user.dto;

public record UserCreateRequest(String firstName,
                                String lastName,
                                String username,
                                String password,
                                Integer availableMoney) {
}
