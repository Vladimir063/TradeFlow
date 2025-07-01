package com.tradeflow.user.service.impl;

import com.tradeflow.event.OrderDto;
import com.tradeflow.funds.api.AccountDto;
import com.tradeflow.portfolio.api.command.PortfolioDto;
import com.tradeflow.user.dto.UserDto;
import com.tradeflow.user.entity.Role;
import com.tradeflow.user.entity.User;
import com.tradeflow.user.repository.UserRepository;


import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService   {
    private final UserRepository repository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public User save(User user) {
        return repository.save(user);
    }


    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        if (repository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }
        return save(user);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }


//    public UserDto getUserInfo(String userName) {
//        RestTemplate restTemplate = new RestTemplate();
//        User user = userRepository.findByUsername(userName)
//                .orElseThrow(() -> new RuntimeException("User not found: " + userName));
//        UserDto userDto = userMapper.toDto(user);
//        AccountDto accountDto = restTemplate.getForObject("http://localhost:8082/api/account/" + user.getUserId(), AccountDto.class);
//        PortfolioDto portfolioDto = restTemplate.getForObject("http://localhost:8085/api/portfolio/" + user.getUserId(), PortfolioDto.class);
//        ResponseEntity<List<OrderDto>> response = restTemplate.exchange(
//                "http://localhost:8080/api/order/user/" + user.getUserId(),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<OrderDto>>() {
//                }
//        );
//        List<OrderDto> orders = response.getBody();
//        userDto.setAccountDto(accountDto);
//        userDto.setPortfolioDto(portfolioDto);
//        userDto.setOrdersDto(orders);
//        return userDto;
//    }


    /**
     * Выдача прав администратора текущему пользователю
     * <p>
     * Нужен для демонстрации
     */
    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }
}

