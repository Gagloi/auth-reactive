package com.potato.auth.service;

import com.potato.auth.domain.User;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserService {

    Mono<User> loadUserByUsername(String username);

    Mono<User> saveUser(User user);

    Mono<Void> deleteUser(String id);

    Mono<Boolean> existUserByLogin(String login);

    Mono<PageImpl<User>> getUsers(Pageable pageable);

    Mono<User> activateUser(String login);

    Mono<User> setRole(String login, List<String> roles);

    Mono<Void> restartApp();

}
