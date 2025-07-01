package com.tradeflow.user.dto;

import lombok.Data;


public record SignInRequest(String username, String password) {

}
