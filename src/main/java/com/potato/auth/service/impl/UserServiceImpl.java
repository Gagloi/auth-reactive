package com.potato.auth.service.impl;

import com.potato.auth.ReactiveAuthApplication;
import com.potato.auth.domain.Role;
import com.potato.auth.domain.User;
import com.potato.auth.domain.UserRoles;
import com.potato.auth.exceptions.RestException;
import com.potato.auth.repository.RoleRepository;
import com.potato.auth.repository.UserRepository;
import com.potato.auth.repository.UserRolesRepository;
import com.potato.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Override
    public Mono<User> loadUserByUsername(String username) {
        return userRepository.findByLogin(username)
                .flatMap(this::loadRelations)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new RestException("User does not exist", 404))));
    }

    @Override
    public Mono<User> saveUser(User user) {
        return userRepository.findByLogin(user.getLogin())
                .flatMap(it -> it.getId() != null ? Mono.defer(() -> Mono.error(new RestException("User already exist", 500))) : userRepository.save(user))
                .switchIfEmpty(userRepository.save(user));
    }

    @Override
    public Mono<Void> deleteUser(String id) {
        return userRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public Mono<Boolean> existUserByLogin(String login) {
        return userRepository.existsUserByLogin(login);
    }

    @Override
    public Mono<PageImpl<User>> getUsers(Pageable pageable) {
        return userRepository.countAll()
                .flatMap(totalSize -> userRepository.findAll()
                        .skip((long) pageable.getPageNumber() * pageable.getPageSize()).take(pageable.getPageSize())
                        .flatMap(this::loadRelations)
                        .collectList().flatMap(res -> Mono.just(new PageImpl<>(res, Pageable.unpaged(), totalSize))));
    }

    @Override
    public Mono<User> activateUser(String login) {
        return userRepository.findByLogin(login)
                .flatMap(it ->
                {
                        it.setEnabled(true);
                        return userRepository.save(it);
                });
    }

    @Override
    public Mono<User> setRole(String login, List<String> roles) {
        return userRepository.findByLogin(login)
                .flatMap(user -> roleRepository.findByNameIn(roles)
                        .collectList()
                        .flatMap(it -> {
                            List<UserRoles> existedRoles = new ArrayList<>();
                            for (Role role : it) {
                                existedRoles.add(new UserRoles(user.getId(), role.getId()));
                            }
                            return userRolesRepository.saveAll(existedRoles).collectList()
                                    .flatMap(res -> Mono.just(user).flatMap(this::loadRelations));
                        }));
    }

    @Override
    public Mono<Void> restartApp() {
        Thread restartThread = new Thread(() -> {
            try {
                Thread.sleep(1000);
                ReactiveAuthApplication.restart();
            } catch (InterruptedException ignored) {
            }
        });
        restartThread.setDaemon(false);
        restartThread.start();
        return Mono.fromRunnable(restartThread);
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
