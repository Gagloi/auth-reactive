package com.potato.auth.service.auth.register.impl;

import com.potato.auth.domain.Role;
import com.potato.auth.domain.UserRoles;
import com.potato.auth.repository.RoleRepository;
import com.potato.auth.repository.UserRepository;
import com.potato.auth.repository.UserRolesRepository;
import com.potato.auth.service.auth.register.SaveUserService;
import com.potato.auth.domain.User;
import com.potato.auth.domain.dto.RegisterDto;
import com.potato.auth.mapping.impl.RegisterToUserMapper;
import com.potato.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class SaveUserServiceImp implements SaveUserService {

    @Autowired
    private RegisterToUserMapper registerToUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Override
    public Mono<User> saveUser(RegisterDto registerDto) {
        User user = registerToUserMapper.toEntity(registerDto);
        user.setPasswordHash(passwordEncoder.encode(registerDto.getPassword()));
        user.setEnabled(true);
        user.setData(registerDto.getData());

        if (registerDto.getRoles() != null && !registerDto.getRoles().isEmpty()) {
        return userService.saveUser(user).flatMap(savedUser -> roleRepository.findByNameIn(registerDto.getRoles())
                .collectList()
                .flatMap(it -> {
                    List<UserRoles> roles = new ArrayList<>();
                    for (Role role : it) {
                        roles.add(new UserRoles(savedUser.getId(), role.getId()));
                    }
                    return userRolesRepository.saveAll(roles).collectList()
                            .flatMap(res -> { return Mono.just(savedUser).flatMap(this::loadRelations);}
                    );
                })
            );
        }
        return userService.saveUser(user);
    }

    private Mono<User> loadRelations(final User user) {
        Mono<User> mono = Mono.just(user)
                .zipWith(roleRepository.findRoleByUserId(user.getId()).collectList())
                .map(result -> {
                    result.getT1().setRoles(result.getT2());
                    return result.getT1();
                });
        return mono;

    }
}
