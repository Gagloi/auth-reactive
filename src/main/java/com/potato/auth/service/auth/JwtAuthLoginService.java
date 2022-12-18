package com.potato.auth.service.auth;

import com.potato.auth.repository.ContextRepository;
import com.potato.auth.service.auth.login.AdditionalUserProcessingService;
import com.potato.auth.service.auth.login.CheckRetrievedUserService;
import com.potato.auth.service.auth.login.RetrieveUserService;
import com.potato.auth.domain.User;
import com.potato.auth.domain.dto.LoginDto;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Service
public class JwtAuthLoginService extends AuthLoginProcess {

    @Autowired
    public RetrieveUserService retrieveUserService;

    @Autowired
    public ContextRepository contextRepository;

    @Autowired
    public CheckRetrievedUserService checkRetrievedUserService;

    @Autowired
    public Map<String, AdditionalUserProcessingService> additionalUserProcessingServices;

    @Autowired
    public Map<String, FormResponseService> formResponseServiceMap;

    @Override
    Mono<User> retrieveUser(LoginDto loginDto) {
        return retrieveUserService.process(loginDto);
    }

    @Override
    Mono<User> checkUser(User user, LoginDto loginDto) {
        return checkRetrievedUserService.checkUser(user, loginDto);
    }

    @Override
    Mono<User> additionalProcessing(User user) {
        return contextRepository.findById(1L).flatMap(context -> {
            Map<String, Boolean> maps = (Map) ((Map)context.getContext().get("jwt")).get("additionalUserProcessingServices");
            List<String> additionalServices = maps.entrySet().stream().filter(it -> it.getValue()).map(at -> at.getKey()).collect(Collectors.toList());
            additionalServices.forEach(it -> additionalUserProcessingServices.get(it).process(user));
            return Mono.just(user);
        });
    }

    @Override
    Mono<ServerResponse> formResponse(User user) {
        return formResponseServiceMap.get("formResponseWhenLoginJWTService").formResponse(user);
    }

}
