package com.tradeflow.user.controller;


import com.tradeflow.user.dto.UserDto;
import com.tradeflow.user.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @GetMapping("/test")
    public String getTest() {
        return "test";
    }
}