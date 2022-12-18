package com.potato.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    private Long id;

    private String refreshToken;

    private String sessionId;

    private Instant expireDate;

    private User user;

}
