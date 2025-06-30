package com.tradeflow.user.service;

import com.tradeflow.user.dto.UserCreateRequest;
import com.tradeflow.user.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDto createUser(UserCreateRequest userCreateRequest);

    UserDto getUserById(UUID userId);

    List<UserDto> getAllUsers();

    UserDto updateUser(UUID userId, UserDto userDto);

    void deleteUser(UUID userId);

    UserDto getUserByUserName(String userName);
}