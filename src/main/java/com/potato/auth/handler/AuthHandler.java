package com.potato.auth.handler;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.potato.auth.domain.RegisterType;
import com.potato.auth.domain.User;
import com.potato.auth.domain.dto.LoginDto;
import com.potato.auth.domain.dto.RegisterDto;
import com.potato.auth.repository.ContextRepository;
import com.potato.auth.service.UserService;
import com.potato.auth.service.auth.AuthLoginProcess;
import com.potato.auth.service.auth.AuthRegisterProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

@Component
@DependsOn("contextRepository")
public class AuthHandler {

    @Autowired
    private AuthLoginProcess authLoginProcess;

    @Autowired
    private AuthRegisterProcess authRegisterProcess;

    @Autowired
    private ContextRepository contextRepository;

    @Value("${google.client_id}")
    private String clientId;

    @Autowired
    private UserService userService;

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginDto.class)
                .flatMap(it -> authLoginProcess.logIn(it));
    }

    public Mono<ServerResponse> register(ServerRequest request) {
        return request.bodyToMono(RegisterDto.class)
                .flatMap(registerDto -> authRegisterProcess.register(registerDto));
    }

    public Mono<ServerResponse> googleAuthHandler(ServerRequest request) {
        return request.bodyToMono(Map.class).flatMap(body -> contextRepository.findById(1L).flatMap(it -> {
            String clientId = (String) ((Map) it.getContext().get("general")).get("clientId");
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(clientId))
                    .build();

            GoogleIdToken idToken = null;
            try {
                idToken = verifier.verify(
                        body.get("idToken").toString()
                );
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                String userId = payload.getSubject();
                String email = payload.getEmail();
                boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());

                return userService.existUserByLogin(email).flatMap(isUserExist ->
                        {
                            if (!isUserExist) {
                                return userService.saveUser(User.builder()
                                        .roles(new ArrayList<>())
                                        .login(email)
                                        .passwordHash("")
                                        .enabled(emailVerified)
                                        .registerType(RegisterType.GMAIL)
                                        .build());
                            } else {
                                return userService.loadUserByUsername(email);
                            }
                        }
                ).flatMap(savedUser -> authLoginProcess.logIn(LoginDto.builder()
                        .login(savedUser.getLogin())
                        .password("")
                        .build())
                );
            } else {
                System.out.println("Invalid ID token.");
                return ServerResponse.status(500).build();
            }
        }));
    }

}
