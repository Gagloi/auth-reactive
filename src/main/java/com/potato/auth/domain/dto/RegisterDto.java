package com.potato.auth.domain.dto;

import com.potato.auth.domain.RegisterType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    private String login;

    private String password;

    private RegisterType registerType;

    private Map<String, Object> data;

    private List<String> roles;

}
