package com.potato.auth.service.auth;

import com.potato.auth.domain.Context;
import com.potato.auth.repository.ContextRepository;
import com.potato.auth.service.auth.register.AdditionalRegisterProcessingService;
import com.potato.auth.service.auth.register.SaveUserService;
import com.potato.auth.domain.User;
import com.potato.auth.domain.dto.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtAuthRegisterService extends AuthRegisterProcess {

    @Autowired
    private SaveUserService saveUserService;

    @Autowired
    private ContextRepository contextRepository;

    @Autowired
    private Map<String, AdditionalRegisterProcessingService> additionalRegisterProcessingServices;

    @Autowired
    public Map<String, FormResponseService> formResponseServiceMap;

    @Override
    Mono<User> saveUser(RegisterDto registerDto) {
        return saveUserService.saveUser(registerDto);
    }

    @Override
    Mono<User> additionalProcessing(RegisterDto registerDto, User user) {
        return contextRepository.findById(1L).flatMap(context -> {
            Map<String, Boolean> maps = (Map) ((Map)context.getContext().get("jwt")).get("additionalRegisterProcessingServices");
            List<String> additionalServices = maps.entrySet().stream().filter(it -> it.getValue()).map(at -> at.getKey()).collect(Collectors.toList());
            additionalServices.forEach(it -> additionalRegisterProcessingServices.get(it).process(registerDto, user));
            return Mono.just(user);
        });
    }

    @Override
    Mono<ServerResponse> formResponse(User user) {
        return formResponseServiceMap.get("formResponseWhenRegisterImpl").formResponse(user);
    }
}
