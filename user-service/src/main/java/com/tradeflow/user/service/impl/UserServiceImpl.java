package com.tradeflow.user.service.impl;

import com.tradeflow.user.dto.UserCreateRequest;
import com.tradeflow.user.dto.UserDto;
import com.tradeflow.user.entity.User;
import com.tradeflow.user.mapper.UserMapper;
import com.tradeflow.user.repository.UserRepository;
import com.tradeflow.user.service.UserEventProducer;
import com.tradeflow.user.service.UserService;
import com.tradeflow.event.UserCreatedEvent;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserEventProducer userEventProducer;


    @Override
    public UserDto createUser(UserCreateRequest userCreateRequest) {
        User user = userMapper.toEntity(userCreateRequest);
        User userSaved = userRepository.save(user);
        UserCreatedEvent userCreatedEvent = userMapper.toUserCreatedEvent(userSaved);
        userEventProducer.publishUserCreatedEvent(userCreatedEvent);
        log.info("Created user: {}", userSaved);
        return userMapper.toDto(userSaved);
    }

    @Override
    public UserDto getUserById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserDto updateUser(UUID userId, UserDto userDto) {
        User updated = userRepository.findById(userId)
                .map(existing -> {
                    existing.setFirstName(userDto.firstName());
                    existing.setLastName(userDto.lastName());
                    return userRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        return userMapper.toDto(updated);
    }

    @Override
    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }
}