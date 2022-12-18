package com.potato.auth.mapping.impl;

import com.potato.auth.domain.RegisterType;
import com.potato.auth.domain.Role;
import com.potato.auth.domain.User;
import com.potato.auth.domain.dto.RegisterDto;
import com.potato.auth.mapping.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RegisterToUserMapper implements Mapper<RegisterDto, User> {

    @Override
    public RegisterDto toDto(User user) {
        return null;
    }

    @Override
    public User toEntity(RegisterDto registerDto) {
        return User.builder()
                .registerType(registerDto.getRegisterType() == null ? RegisterType.EMAIL : registerDto.getRegisterType())
                .login(registerDto.getLogin())
                .passwordHash(registerDto.getPassword())
                .data(registerDto.getData())
                .roles(List.of())
                .enabled(false)
                .build();
    }

}
