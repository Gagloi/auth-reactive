package com.potato.auth.domain.dto;

import com.potato.auth.domain.RegisterType;
import lombok.Builder;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
public class UserDto {

    private UUID id;

    private String login;

    private String passwordHash;

    private Boolean enabled;

    private RegisterType registerType;

    private Set<Long> roleIds;

}
