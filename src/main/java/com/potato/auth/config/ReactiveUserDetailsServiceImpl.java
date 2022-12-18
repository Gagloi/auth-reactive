package com.potato.auth.config;

import com.potato.auth.domain.Role;
import com.potato.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userService.loadUserByUsername(username)
                .flatMap(it -> Mono.just(User.builder()
                        .disabled(it.getEnabled())
                        .password(it.getPasswordHash())
                        .authorities(mapRolesToAuthorities(it.getRoles()))
                        .username(it.getLogin())
                        .build())
                );
    }

    private List<SimpleGrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream().map(it -> new SimpleGrantedAuthority(it.getName())).collect(Collectors.toUnmodifiableList());
    }
}
