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

//    @GetMapping("/{username}")
//    public UserDto getUserInfo(@PathVariable String userName) {
//        return userService.getUserInfo(userName);
//    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String exampleAdmin() {
        return "Hello, admin!";
    }

//    @GetMapping("/get-admin")
//    public void getAdmin() {
//        service.getAdmin();
//    }
}