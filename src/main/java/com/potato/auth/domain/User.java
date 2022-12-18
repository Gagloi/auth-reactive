package com.potato.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("users")
public class User implements Serializable {

    @Id
    @Column("id")
    private UUID id;

    @Column("login")
    private String login;

    private String passwordHash;

    private Boolean enabled = false;

    private RegisterType registerType;

    private Map<String, Object> data = new LinkedHashMap<>();

    @Transient
    private List<Role> roles = new ArrayList<>();

}
