package com.tradeflow.user.service.impl;

import com.tradeflow.event.OrderDto;
import com.tradeflow.funds.api.AccountDto;
import com.tradeflow.portfolio.api.command.PortfolioDto;
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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDto createUser(UserCreateRequest userCreateRequest) {
        User user = userMapper.toEntity(userCreateRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userSaved = userRepository.save(user);
        UserCreatedEvent userCreatedEvent = userMapper.toUserCreatedEvent(userSaved);
        userEventProducer.publishUserCreatedEvent(userCreatedEvent);
        log.info("Created user: {}", userSaved);
        return userMapper.toDto(userSaved);
    }

    @Override
    public UserDto getUserById(UUID userId) {
        RestTemplate restTemplate = new RestTemplate();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        UserDto userDto = userMapper.toDto(user);
        AccountDto accountDto = restTemplate.getForObject("http://localhost:8082/account/" + userId, AccountDto.class);
        PortfolioDto portfolioDto = restTemplate.getForObject("http://localhost:8085/portfolio/" + userId, PortfolioDto.class);
        ResponseEntity<List<OrderDto>> response = restTemplate.exchange(
                "http://localhost:8080/order/user/" + userId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OrderDto>>() {
                }
        );
        List<OrderDto> orders = response.getBody();
        userDto.setAccountDto(accountDto);
        userDto.setPortfolioDto(portfolioDto);
        userDto.setOrdersDto(orders);
        return userDto;
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
                    existing.setFirstName(userDto.getFirstName());
                    existing.setLastName(userDto.getLastName());
                    return userRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        return userMapper.toDto(updated);
    }

    @Override
    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserDto getUserByUserName(String userName) {
        RestTemplate restTemplate = new RestTemplate();
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("User not found: " + userName));
        UserDto userDto = userMapper.toDto(user);
        AccountDto accountDto = restTemplate.getForObject("http://localhost:8082/api/account/" + user.getUserId(), AccountDto.class);
        PortfolioDto portfolioDto = restTemplate.getForObject("http://localhost:8085/api/portfolio/" + user.getUserId(), PortfolioDto.class);
        ResponseEntity<List<OrderDto>> response = restTemplate.exchange(
                "http://localhost:8080/api/order/user/" + user.getUserId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OrderDto>>() {
                }
        );
        List<OrderDto> orders = response.getBody();
        userDto.setAccountDto(accountDto);
        userDto.setPortfolioDto(portfolioDto);
        userDto.setOrdersDto(orders);
        return userDto;
    }
}