package com.potato.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "context")
@Builder
public class Context {

    @Id
    private Long id;

    private Map<String, Object> context;

}
