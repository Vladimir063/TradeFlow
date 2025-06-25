package com.tradeflow.user.mapper;

import com.tradeflow.user.dto.UserCreateRequest;
import com.tradeflow.user.dto.UserDto;
import com.tradeflow.user.entity.User;
import com.tradeflow.event.UserCreatedEvent;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserCreateRequest userCreateRequest);

    UserCreatedEvent toUserCreatedEvent(User user);
}